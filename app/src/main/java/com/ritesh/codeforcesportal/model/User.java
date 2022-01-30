package com.ritesh.codeforcesportal.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String handle;
    private String email;
    private String vkId;
    private String openId;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String organization;
    private int contribution;
    private String rank;
    private int rating;
    private String maxRank;
    private int maxRating;
    private int lastOnlineTimeSeconds;
    private int registrationTimeSeconds;
    private int friendOfCount;
    private String avatar;
    private String titlePhoto;


    protected User(Parcel in) {
        handle = in.readString();
        email = in.readString();
        vkId = in.readString();
        openId = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        country = in.readString();
        city = in.readString();
        organization = in.readString();
        contribution = in.readInt();
        rank = in.readString();
        rating = in.readInt();
        maxRank = in.readString();
        maxRating = in.readInt();
        lastOnlineTimeSeconds = in.readInt();
        registrationTimeSeconds = in.readInt();
        friendOfCount = in.readInt();
        avatar = in.readString();
        titlePhoto = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getHandle() {
        return handle;
    }

    public String getEmail() {
        return email;
    }

    public String getVkId() {
        return vkId;
    }

    public String getOpenId() {
        return openId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getOrganization() {
        return organization;
    }

    public int getContribution() {
        return contribution;
    }

    public String getRank() {
        return rank;
    }

    public int getRating() {
        return rating;
    }

    public String getMaxRank() {
        return maxRank;
    }

    public int getMaxRating() {
        return maxRating;
    }

    public int getLastOnlineTimeSeconds() {
        return lastOnlineTimeSeconds;
    }

    public int getRegistrationTimeSeconds() {
        return registrationTimeSeconds;
    }

    public int getFriendOfCount() {
        return friendOfCount;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getTitlePhoto() {
        return titlePhoto;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(handle);
        parcel.writeString(email);
        parcel.writeString(vkId);
        parcel.writeString(openId);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(country);
        parcel.writeString(city);
        parcel.writeString(organization);
        parcel.writeInt(contribution);
        parcel.writeString(rank);
        parcel.writeInt(rating);
        parcel.writeString(maxRank);
        parcel.writeInt(maxRating);
        parcel.writeInt(lastOnlineTimeSeconds);
        parcel.writeInt(registrationTimeSeconds);
        parcel.writeInt(friendOfCount);
        parcel.writeString(avatar);
        parcel.writeString(titlePhoto);
    }
}
