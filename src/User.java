class User {
    String userName;
    int userID;
    String userType;

    public User(int id, String name, String userType) {
        this.userID = id;
        this.userName = name;
        this.userType = userType;
    }

    String getUserName() {
        return userName;
    }

    int getUserID() {
        return userID;
    }

    String getUserType() {
        return userType;
    }

    boolean hasAccessToBMI() {
        return false;
    }
    boolean chooseConsultation() {
        return false;
    }

    boolean canEditPatient() {
        return false;
    }
}

class Huisarts extends User {
    public Huisarts(int id, String name) {
        super(id, name, "Huisarts");
    }

    @Override
    boolean hasAccessToBMI() {
        return true;
    }
    @Override
    boolean canEditPatient() {
        return true;
    }
}

class Tandarts extends User {
    public Tandarts(int id, String name) {
        super(id, name, "Tandarts");
    }

    @Override
    boolean chooseConsultation() {return true;}
    @Override
    boolean hasAccessToBMI() {
        return false;
    }
    @Override
    boolean canEditPatient() {
        return false;
    }


}

class Fysiotherapeut extends User {
    public Fysiotherapeut(int id, String name) {
        super(id, name, "Fysiotherapeut");
    }

    @Override
    boolean hasAccessToBMI() {
        return true;
    }

    @Override
    boolean canEditPatient() {
        return true;
    }
}
class Apotheker extends User {
    public Apotheker(int id, String name) {
        super(id, name, "Apotheker");
    }

    @Override
    boolean hasAccessToBMI() {
        return true;
    }

    @Override
    boolean canEditPatient() {
        return false;
    }
}