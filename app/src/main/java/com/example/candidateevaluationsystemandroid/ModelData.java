package com.example.candidateevaluationsystemandroid;

public class ModelData {
        private String mname;
        private String memail;
        private String madderess;
        private String mcontact;
        private String mscore;

        public ModelData() {}

       /* private ModelData(String name,String email,String adderess,String contact,String score)
        {
            this.mname = name;
            this.memail = email;
            this.madderess = adderess;
            this.mcontact = contact;
            this.mscore = score;
        }*/

        public String getMname() {
            return mname;
        }

        public void setMname(String mname) {
            this.mname = mname;
        }

        public String getMemail() {
            return memail;
        }

        public void setMemail(String memail) {
            this.memail = memail;
        }

        public String getMadderess() {
            return madderess;
        }

        public void setMadderess(String madderess) {
            this.madderess = madderess;
        }

        public String getMcontact() {
            return mcontact;
        }

        public void setMcontact(String mcontact) {
            this.mcontact = mcontact;
        }

        public String getMscore() {
            return mscore;
        }

        public void setMscore(String mscore) {
            this.mscore = mscore;
        }
    }
