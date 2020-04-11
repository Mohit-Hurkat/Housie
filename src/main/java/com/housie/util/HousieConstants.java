package com.housie.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class HousieConstants {

    public final static Set<String> tobeUpdatedKeysForRoomNumber = new HashSet<>(Arrays.asList("calledNumbers","uncalledNumbers","lastNumberRemovedAt"));
    public final static Set<String> upsertKeysForRoom = new HashSet<>(Arrays.asList("code","phoneNumber"));


}
