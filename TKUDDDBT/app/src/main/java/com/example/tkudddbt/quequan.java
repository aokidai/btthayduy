package com.example.tkudddbt;

public class quequan {
    private String idQue;
    private String QueQuan;

    public quequan(String idQue, String queQuan) {
        this.idQue = idQue;
        QueQuan = queQuan;
    }

    public quequan(String queQuan) {
        QueQuan = queQuan;
    }

    public String getIdQue() {
        return idQue;
    }

    public void setIdQue(String idQue) {
        this.idQue = idQue;
    }

    public String getQueQuan() {
        return QueQuan;
    }

    public void setQueQuan(String queQuan) {
        QueQuan = queQuan;
    }

    @Override
    public String toString() {
        return this.QueQuan;
    }
}
