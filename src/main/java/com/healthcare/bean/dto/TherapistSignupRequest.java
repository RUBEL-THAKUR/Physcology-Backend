// Location: src/main/java/com/healthcare/bean/dto/TherapistSignupRequest.java

package com.healthcare.bean.dto;

public class TherapistSignupRequest {
    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
    private String mobileNumber;
    private boolean isAbove18;
    private boolean acceptedTerms;

    // Getters and Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmailId() { return emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public boolean isAbove18() { return isAbove18; }
    public void setAbove18(boolean above18) { isAbove18 = above18; }

    public boolean isAcceptedTerms() { return acceptedTerms; }
    public void setAcceptedTerms(boolean acceptedTerms) { this.acceptedTerms = acceptedTerms; }
}