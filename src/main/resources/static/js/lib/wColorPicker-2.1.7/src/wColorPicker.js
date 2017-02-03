(function($) {
  function ColorPicker(el, options) {
    this.$el = $(el);
    this.options = options;
    this.init = false;

    this.generate();
  }
    
  ColorPicker.prototype = {
    generate: function() {
      var _this = this;

      if (!this.$colorPicker) {
        // pallette holders
        this.$noneColorPalette = this._createPalette('none', [['transparent']]);
        this.$simpleColorPalette = this._createPalette('simple', $.fn.wColorPicker.simpleColors);
        this.$mixedColorPalette = this._createPalette('mixed', $.fn.wColorPicker.mixedColors);

        // input and target holders
        this.$colorTarget = $('<div class="wColorPicker-color-target"></div>');
        this.$customInput = $('<input type="text" class="wColorPicker-custom-input"/>').keyup($.proxy(this._customInputKeyup, this));
        
        if (this.options.dropperButton) { this.$dropperButton = this._createDropperButton(); }

        // the Palettes holder is to enforce size of color picker for effects so no weird bunching up happens when we change sizes
        this.$colorPickerPalettesHolder = $('<div class="wColorPicker-palettes-holder"></div>')
        .append(this.$noneColorPalette)
        .append(this.$colorTarget)
        .append(this.$customInput)
        .append(this.$dropperButton)
        .append('<br/>')
        .append(this.$simpleColorPalette)
        .append(this.$mixedColorPalette);

        // the holder is what we will use on some effects, rather than the pallettes holder which need to remain the same size
        this.$colorPickerHolder = $('<div class="wColorPicker-holder"></div>').append(this.$colorPickerPalettesHolder);
        
        // store our bg independantly this is due to a bug with opacities showing up oddly 
        this.$colorPickerBg = $('<div class="wColorPicker-bg"></div>');

        // put everything into the color picker
        // make sure title="" here otherwise title from button will appear
        // also bind some events to not propagate in case some kind of tooltip plugin is set
        this.$colorPicker = $('<div class="wColorPicker" title=""></div>')
        .mouseenter(function(e){ e.stopPropagation(); })
        .bind('mouseenter mousemove click', function(e){ e.stopPropagation(); })
        .append(this.$colorPickerBg).append(this.$colorPickerHolder);

        // set some properties
        this.setOpacity(this.options.opacity);
        this.setTheme(this.options.theme);
        this.setColor(this.options.color);

        // get proper width/height and keep it for reference (needed for effects)
        $('body').append(this.$colorPicker);
        this.width = this.$colorPickerPalettesHolder.width();
        this.height = this.$colorPickerPalettesHolder.height();

        // set these explicitly so we don't get bunching when running effects
        this.$colorPickerPalettesHolder.width(this.width);
        this.$colorPickerPalettesHolder.height(this.height);

        // append the color picker properly
        this.$el.append(this.$colorPicker);

        // set mode after to get proper dimensions
        // if hidden will not get proper dimensions
        this.setMode(this.options.mode);

        // call after we have our proper dimensions set
        // setPosition will call setEffect
        this.setPosition(this.options.position);
      }

      this.init = true;

      return this;
    },

    /************************************
     * Setters
     ************************************/

    setTheme: function(theme) {
      this.$colorPicker.attr('class', this.$colorPicker.attr('class').replace(/wColorPicker-theme-.+\s|wColorPicker-theme-.+$/, ''));
      this.$colorPicker.addClass('wColorPicker-theme-' + theme);
    },

    setOpacity: function(opacity) {
      this.$colorPickerBg.css('opacity', opacity);
    },

    setColor: function(color) {
      if (!window.rgbHex(color)) { return true; }

      this.options.color = color;
      this.$colorTarget.css('backgroundColor', color);
      this.$customInput.val(color);

      if (this.init && this.options.onSelect) { this.options.onSelect.apply(this, [color]); }
    },

    setMode: function(mode) {
      var _this = this,
          showFunc = function(){ _this._toggleEffect('show'); },
          hideFunc = function(){ _this._toggleEffect('hide'); };

      if (mode === 'flat') { this.$colorPicker.removeClass('wColorPicker-zindex').css({position:'relative', display:''}); }
      else { this.$colorPicker.addClass('wColorPicker-zindex').css({position:'absolute'}).hide(); }

      // incase button needs to be reset
      this.$el.find('wColorPicker-button').remove();
      this.$el.unbind('mouseenter', showFunc).unbind('mouseleave', hideFunc);
      $(document).unbind('click', hideFunc);

      // regenerate button
      if (mode !== 'flat') {
        var $button = null,
            $buttonColor = null,
            func = function(e) {
              e.stopPropagation();
              if(_this.options.generateButton) { $buttonColor.css('backgroundColor', _this.options.color); }
              _this._toggleEffect();
            };

        if(this.options.generateButton) {
          $button = $('<div class="wColorPicker-button"></div>');
          $buttonColor = $('<div class="wColorPicker-button-color"></div>').css('backgroundColor', this.options.color);

          this.$el.append($button);
          $button.append($buttonColor.height(this.$el.height() - $button.outerHeight(true)));
        }

        // toggle effect if palette is clicked
        this.$noneColorPalette.bind('click', func);
        this.$simpleColorPalette.bind('click', func);
        this.$mixedColorPalette.bind('click', func);
      }

      // regenerate events
      if (mode === 'click') {
        this.$el.click(function(e){
          _this._toggleEffect();
          e.stopPropagation(); // so doc event doesn't run
        });

        this.$colorPicker.click(function(e){ e.stopPropagation(); });
        $(document).bind('click', hideFunc);
      }
      else if (mode === 'hover') {
        this.$el.bind('mouseenter', showFunc).bind('mouseleave', hideFunc);
      }
    },

    setEffect: function(effect) {
      if (this.options.mode === 'flat') { return true; }

      // reset
      this.$colorPicker.css('opacity', 1);
      this.$colorPickerHolder.width(this.width).height(this.height);

      if (effect === 'fade') {
        this.$colorPicker.css('opacity', 0);
      }
      else if (effect === 'slide') {
        this.$colorPickerHolder
        .width(this.positionAxis === 'x' ? 0 : this.width)
        .height(this.positionAxis === 'y' ? 0 : this.height);
      }
    },

    setPosition: function(position) {
      if (this.options.mode === 'flat') { return true; }

      var elWidth = this.$el.outerWidth(),
          elHeight = this.$el.outerHeight(),
          center = (this.$el.outerWidth()/2) - (this.$colorPicker.outerWidth()/2),
          middle = (this.$el.outerHeight()/2) - (this.$colorPicker.outerHeight()/2),
          css = {left:'', right:'', top:'', bottom:''},
          firstChar = this.options.position.charAt(0);

      // so we know which way to do our slide effects
      if (firstChar === 't' || firstChar === 'b') { this.positionAxis = 'y'; }
      else if(firstChar === 'l' || firstChar === 'r') { this.positionAxis = 'x'; }

      switch (position) {
        case 'tl': css.left=0;        css.bottom=elHeight; break;
        case 'tc': css.left=center;   css.bottom=elHeight; break;
        case 'tr': css.right=0;       css.bottom=elHeight; break;
        case 'rt': css.left=elWidth;  css.top=0;           break;
        case 'rm': css.left=elWidth;  css.top=middle;      break;
        case 'rb': css.left=elWidth;  css.bottom=0;        break;
        case 'br': css.right=0;       css.top=elHeight;    break;
        case 'bc': css.left=center;   css.top=elHeight;    break;
        case 'bl': css.left=0;        css.top=elHeight;    break;
        case 'lb': css.right=elWidth; css.bottom=0;        break;
        case 'lm': css.right=elWidth; css.top=middle;      break;
        case 'lt': css.right=elWidth; css.top=0;           break;
      }

      this.$colorPicker.css(css);
      this.setEffect(this.options.effect);
    },

    /************************************
     * Components
     ************************************/

    _createPalette: function(name, colors) {
      var i=0, ii=0, j=0, jj=0, $div=null,
          $el = $('<div class="wColorPicker-palette wColorPicker-palette-' + name + '"></div>');

      for (i=0, ii=colors.length; i<ii; i++) {
        for (j=0, jj=colors[i].length; j<jj; j++) {
          $div = this._createColor(j, colors[i][j]);
          
          if (i === 0) { $div.addClass('wColorPicker-palette-color-top'); }
          if (j === 0) { $div.addClass('wColorPicker-palette-color-left'); }

          $el.append($div);
        }

        if (i<ii) { $el.append('<br/>'); }
      }

      return $el;
    },

    _createColor: function(index, color) {
      var _this = this;

      return $('<div class="wColorPicker-palette-color"></div>')
        .attr('id', 'wColorPicker-palette-color-' + index)
        .addClass('wColorPicker-palette-color-' + index)
        .css('backgroundColor', color)
        .hover(
          function(){ _this._colorMouseenter($(this)); },
          function(){ _this._colorMouseleave($(this)); }
        )
        .click(function(e) {
          _this.setColor(window.rgbHex($(this).css('backgroundColor')));
        });
    },

    _createDropperButton: function() {
      return $('<div class="wColorPicker-dropper"></div>').click($.proxy(this.options.onDropper, this));
    },

    /************************************
     * Events
     ************************************/

    _customInputKeyup: function(e) {
        var val = $(e.target).val();
        
        if (window.rgbHex(val)) {
          this.$colorTarget.css('backgroundColor', val);
          if (e.keyCode === 13) { this.setColor(val); }
        }
    },

    _colorMouseenter: function($el) {
      var color = window.rgbHex($el.css('backgroundColor'));

      $el.addClass('active').prev().addClass('active-right');
      $el.prevAll('.' + $el.attr('id') + ':first').addClass('active-bottom');
      
      this.$colorTarget.css('backgroundColor', color);
      this.$customInput.val(color);
      
      if (this.options.onMouseover) { this.options.onMouseover.apply(this, [color]); }
    },
    
    _colorMouseleave: function($el) {
      $el.removeClass('active').prev().removeClass('active-right');
      $el.prevAll('.' + $el.attr('id') + ':first').removeClass('active-bottom');
      
      this.$colorTarget.css('backgroundColor', this.options.color);
      this.$customInput.val(this.options.color);
      
      if (this.options.onMouseout) { this.options.onMouseout.apply(this, [this.options.color]); }
    },

    /************************************
     * Effects
     ************************************/

    _toggleEffect: function(toggle) {
      var visible = this.$colorPicker.hasClass('wColorPicker-visible');

      // to make sure some effects only occur on a certain state (to avoid an effect running twice and retoggling)
      if(
        !toggle ||
        (toggle === 'show' && visible === false) ||
        (toggle === 'hide' && visible === true)
      ) {
        if(!visible) { this.setPosition(this.options.position); }
        this['_' + this.options.effect + 'Effect' + (visible ? 'Hide' : 'Show')]();
        this.$colorPicker.toggleClass('wColorPicker-visible');
      }
    },

    /* none */
    _noneEffectShow: function() {
      this.$colorPicker.css('display', 'inline-block');
    },

    _noneEffectHide: function() {
      this.$colorPicker.hide();
    },

    /* fade */
    _fadeEffectShow: function() {
      this.$colorPicker.stop(true, false).css({display:'inline-block'}).animate({opacity:1}, this.options.showSpeed);
    },

    _fadeEffectHide: function() {
      this.$colorPicker.stop(true, false).animate({opacity:0}, this.options.hideSpeed, $.proxy(function(){ this.$colorPicker.hide(); }, this));
    },

    /* slide */
    _slideEffectShow: function() {
      var animate = this.positionAxis === 'y' ? {height:this.height} : {width:this.width};

      this.$colorPicker.css('display', 'inline-block');
      this.$colorPickerHolder.stop(true, false).animate(animate, this.options.showSpeed);
    },

    _slideEffectHide: function() {
      var animate = this.positionAxis === 'y' ? {height:0} : {width:0};
      this.$colorPickerHolder.stop(true, false).animate(animate, this.options.hideSpeed, $.proxy(function(){ this.$colorPicker.hide(); }, this));
    }
  };

  $.fn.wColorPicker = function(options, value) {
    if (typeof options === 'string') {
      var values = [], wColorPicker = null, elements = null, func = null;
        
      elements = this.each(function() {
        wColorPicker = $(this).data('wColorPicker');

        if (wColorPicker) {
          func = (value ? 'set' : 'get') + options.charAt(0).toUpperCase() + options.substring(1).toLowerCase();

          if (wColorPicker[options]) {
            values.push(wColorPicker[options].apply(wColorPicker, [value]));
          }
          else if (value) {
            if (wColorPicker[func]) { wColorPicker[func].apply(wColorPicker, [value]); }
            if (wColorPicker.options[options]) { wColorPicker.options[options] = value; }
          }
          else {
            if(wColorPicker[func]) { values.push(wColorPicker[func].apply(wColorPicker, [value])); }
            else if (wColorPicker.options[options]) { values.push(wColorPicker.options[options]); }
            else { values.push(null); }
          }
        }
      });

      if (values.length === 1) { return values[0]; }
      else if (values.length > 0) { return values; }
      else { return elements; }
    }

    function get(el) {
      var _options,
          wColorPicker = $.data(el, 'wColorPicker');
      
      if (!wColorPicker) {
        _options = $.extend({}, $.fn.wColorPicker.defaults, options);
        _options.color = window.rgbHex(_options.color) ? _options.color : 'transparent';

        wColorPicker = new ColorPicker(el, _options);
        $.data(el, 'wColorPicker', wColorPicker);
      }

      return wColorPicker;
    }

    return this.each(function(){ get(this); });
  };
  
  $.fn.wColorPicker.defaults = {
    theme           : 'classic',  // set theme
    opacity         : 0.9,        // opacity level
    color           : '#FF0000',  // set init color
    mode            : 'flat',     // mode for palette (flat, hover, click)
    position        : 'bl',       // position of palette, (tl, tc, tr, rt, rm, rb, br, bc, bl, lb, lm, lt)
    generateButton  : true,       // if mode not flat generate button or not
    dropperButton   : false,      // optional dropper button to use in other apps
    effect          : 'slide',    // only used when not in flat mode (none, slide, fade)
    showSpeed       : 500,        // show speed for effects
    hideSpeed       : 500,        // hide speed for effects
    onMouseover     : null,       // callback for color mouseover
    onMouseout      : null,       // callback for color mouseout
    onSelect        : null,       // callback for color when selected
    onDropper       : null        // callback when dropper is clicked
  };

  $.fn.wColorPicker.mixedColors = [
    ['#000000', '#003300', '#006600', '#009900', '#00CC00', '#00FF00', '#330000', '#333300', '#336600', '#339900', '#33CC00', '#33FF00', '#660000', '#663300', '#666600', '#669900', '#66CC00', '#66FF00'],
    ['#000033', '#003333', '#006633', '#009933', '#00CC33', '#00FF33', '#330033', '#333333', '#336633', '#339933', '#33CC33', '#33FF33', '#660033', '#663333', '#666633', '#669933', '#66CC33', '#66FF33'],
    ['#000066', '#003366', '#006666', '#009966', '#00CC66', '#00FF66', '#330066', '#333366', '#336666', '#339966', '#33CC66', '#33FF66', '#660066', '#663366', '#666666', '#669966', '#66CC66', '#66FF66'],
    ['#000099', '#003399', '#006699', '#009999', '#00CC99', '#00FF99', '#330099', '#333399', '#336699', '#339999', '#33CC99', '#33FF99', '#660099', '#663399', '#666699', '#669999', '#66CC99', '#66FF99'],
    ['#0000CC', '#0033CC', '#0066CC', '#0099CC', '#00CCCC', '#00FFCC', '#3300CC', '#3333CC', '#3366CC', '#3399CC', '#33CCCC', '#33FFCC', '#6600CC', '#6633CC', '#6666CC', '#6699CC', '#66CCCC', '#66FFCC'],
    ['#0000FF', '#0033FF', '#0066FF', '#0099FF', '#00CCFF', '#00FFFF', '#3300FF', '#3333FF', '#3366FF', '#3399FF', '#33CCFF', '#33FFFF', '#6600FF', '#6633FF', '#6666FF', '#6699FF', '#66CCFF', '#66FFFF'],
    ['#990000', '#993300', '#996600', '#999900', '#99CC00', '#99FF00', '#CC0000', '#CC3300', '#CC6600', '#CC9900', '#CCCC00', '#CCFF00', '#FF0000', '#FF3300', '#FF6600', '#FF9900', '#FFCC00', '#FFFF00'],
    ['#990033', '#993333', '#996633', '#999933', '#99CC33', '#99FF33', '#CC0033', '#CC3333', '#CC6633', '#CC9933', '#CCCC33', '#CCFF33', '#FF0033', '#FF3333', '#FF6633', '#FF9933', '#FFCC33', '#FFFF33'],
    ['#990066', '#993366', '#996666', '#999966', '#99CC66', '#99FF66', '#CC0066', '#CC3366', '#CC6666', '#CC9966', '#CCCC66', '#CCFF66', '#FF0066', '#FF3366', '#FF6666', '#FF9966', '#FFCC66', '#FFFF66'],
    ['#990099', '#993399', '#996699', '#999999', '#99CC99', '#99FF99', '#CC0099', '#CC3399', '#CC6699', '#CC9999', '#CCCC99', '#CCFF99', '#FF0099', '#FF3399', '#FF6699', '#FF9999', '#FFCC99', '#FFFF99'],
    ['#9900CC', '#9933CC', '#9966CC', '#9999CC', '#99CCCC', '#99FFCC', '#CC00CC', '#CC33CC', '#CC66CC', '#CC99CC', '#CCCCCC', '#CCFFCC', '#FF00CC', '#FF33CC', '#FF66CC', '#FF99CC', '#FFCCCC', '#FFFFCC'],
    ['#9900FF', '#9933FF', '#9966FF', '#9999FF', '#99CCFF', '#99FFFF', '#CC00FF', '#CC33FF', '#CC66FF', '#CC99FF', '#CCCCFF', '#CCFFFF', '#FF00FF', '#FF33FF', '#FF66FF', '#FF99FF', '#FFCCFF', '#FFFFFF']
  ];

  $.fn.wColorPicker.simpleColors = [
    ['#000000'], ['#333333'], ['#666666'], ['#999999'], ['#CCCCCC'], ['#FFFFFF'], ['#FF0000'], ['#00FF00'], ['#0000FF'], ['#FFFF00'], ['#00FFFF'], ['#FF00FF']
  ];
})(jQuery);