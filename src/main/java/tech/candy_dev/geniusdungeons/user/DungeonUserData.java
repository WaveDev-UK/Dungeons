package tech.candy_dev.geniusdungeons.user;

import tech.candy_dev.candycommons.user.data.UserData;

import java.util.UUID;

public class DungeonUserData extends UserData {

    private double exp = 0;

    public DungeonUserData(UUID id){
        super(id);
    }

    public double getExp() {
        return exp;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }
}
