package uk.ac.ncl.daniel.baranowski.data.access.pojos;

import java.util.Objects;

/**
 * A question stored in a section of a paper.
 */
public final class Question {
    private final int id;
    private final String language;
    private final int difficulty;
    private final String referenceName;
    private final String type;

    private Question(Builder builder) {
        id = builder.id;
        language = builder.language;
        difficulty = builder.difficulty;
        referenceName = builder.referenceName;
        type = builder.type;
    }

    public int getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public String getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, language, difficulty, referenceName, type);
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }

        if (object instanceof Question) {
            Question that = (Question) object;
            return Objects.equals(id, that.getId()) &&  // NOSONAR We need to compare all fields
                    Objects.equals(language, that.getLanguage()) &&
                    Objects.equals(difficulty, that.getDifficulty()) &&
                    Objects.equals(referenceName, that.getReferenceName()) &&
                    Objects.equals(type, that.getType());
        }

        return false;
    }

    @Override
    public String toString() {
        return String.format("Question [id=%s, language=%s, difficulty=%s, referenceName=%s, type=%s]", id, language,
                difficulty, referenceName, type);
    }

    public static class Builder {
        private int id;
        private String language;
        private int difficulty;
        private String referenceName;
        private String type;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setLanguage(String language) {
            this.language = language;
            return this;
        }

        public Builder setDifficulty(int difficulty) {
            this.difficulty = difficulty;
            return this;
        }

        public Builder setReferenceName(String referenceName) {
            this.referenceName = referenceName;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Question build() {
            return new Question(this);
        }
    }
}
