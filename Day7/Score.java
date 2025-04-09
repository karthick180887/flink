package org.apache.flink;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Score {
    @JsonProperty("classID")
    private String classID;
    @JsonProperty("studentID")
    private String studentID;
    @JsonProperty("courseName")
    private String courseName;
    @JsonProperty("score")
    private float score;

    public Score() {
    }

    public Score(String classID, String studentID, String courseName, float score) {
        this.classID = classID;
        this.studentID = studentID;
        this.courseName = courseName;
        this.score = score;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public static ScoreBuilder builder() {
        return new ScoreBuilder();
    }

    public static class ScoreBuilder {
        private String classID;
        private String studentID;
        private String courseName;
        private float score;

        public ScoreBuilder classID(String classID) {
            this.classID = classID;
            return this;
        }

        public ScoreBuilder studentID(String studentID) {
            this.studentID = studentID;
            return this;
        }

        public ScoreBuilder courseName(String courseName) {
            this.courseName = courseName;
            return this;
        }

        public ScoreBuilder score(float score) {
            this.score = score;
            return this;
        }

        public Score build() {
            return new Score(classID, studentID, courseName, score);
        }
    }

    @Override
    public String toString() {
        return "Score{" +
                "classID='" + classID + '\'' +
                ", studentID='" + studentID + '\'' +
                ", courseName='" + courseName + '\'' +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Score score1 = (Score) o;

        if (Float.compare(score1.score, score) != 0) return false;
        if (classID != null ? !classID.equals(score1.classID) : score1.classID != null) return false;
        if (studentID != null ? !studentID.equals(score1.studentID) : score1.studentID != null)
            return false;
        return courseName != null ? courseName.equals(score1.courseName) : score1.courseName == null;
    }

    @Override
    public int hashCode() {
        int result = classID != null ? classID.hashCode() : 0;
        result = 31 * result + (studentID != null ? studentID.hashCode() : 0);
        result = 31 * result + (courseName != null ? courseName.hashCode() : 0);
        result = 31 * result + (score != +0.0f ? Float.floatToIntBits(score) : 0);
        return result;
    }
}