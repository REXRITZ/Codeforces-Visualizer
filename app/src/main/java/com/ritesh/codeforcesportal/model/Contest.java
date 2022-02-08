package com.ritesh.codeforcesportal.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Contest implements Parcelable {
    private final int id;
    private final String name;
    private final String type;
    private final String phase;
    private final boolean frozen;
    private final int durationSeconds;
    private final int startTimeSeconds;
    private final int relativeTimeSeconds;

    protected Contest(Parcel in) {
        id = in.readInt();
        name = in.readString();
        type = in.readString();
        phase = in.readString();
        frozen = in.readByte() != 0;
        durationSeconds = in.readInt();
        startTimeSeconds = in.readInt();
        relativeTimeSeconds = in.readInt();
    }

    public static final Creator<Contest> CREATOR = new Creator<Contest>() {
        @Override
        public Contest createFromParcel(Parcel in) {
            return new Contest(in);
        }

        @Override
        public Contest[] newArray(int size) {
            return new Contest[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getPhase() {
        return phase;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public int getStartTimeSeconds() {
        return startTimeSeconds;
    }

    public int getRelativeTimeSeconds() {
        return relativeTimeSeconds;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(type);
        parcel.writeString(phase);
        parcel.writeByte((byte) (frozen ? 1 : 0));
        parcel.writeInt(durationSeconds);
        parcel.writeInt(startTimeSeconds);
        parcel.writeInt(relativeTimeSeconds);
    }
}

