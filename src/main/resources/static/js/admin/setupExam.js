$(function() {
    var myObj = new candidateFormUiController($(".createCandidate"),$(".pickCandidate"));
    $("#showNewCandidate").click(function(){
        myObj.showCreateCandidate();
    });
    $("#showPickExisting").click(function(){
        myObj.showPickCandidate();
    });
});

class candidateFormUiController{
    constructor(createElements,pickElements) {
        createElements.hide();
        this.createElements = createElements;
        this.pickElements = pickElements;
        this.nullifyInputs(createElements);
        this.nullifyInputs(pickElements);
    }
    showPickCandidate() {
        this.pickElements.show();
        this.createElements.hide();
        this.nullifyInputs(this.createElements);
    }
    showCreateCandidate(){
        this.createElements.show();
        this.pickElements.hide();
        this.nullifyInputs(this.pickElements);
    }
    nullifyInputs(inputs){
        inputs.find(':input').each(
            function(){
                var input = $(this);
                input.val(null);
            }
        );
    }
}