package hk.hkucs.yellowobjects.ui.poster_page;

public class Event {
    String image, title, desc, id, venue;
    int starty, startm, startd, starth, starti, starts;
    int endy, endm, endd, endh, endi, ends;

    public String getVenue() {
        return venue;
    }

    public int getStarty() {
        return starty;
    }

    public int getStartm() {
        return startm;
    }

    public int getStartd() {
        return startd;
    }

    public int getStarth() {
        return starth;
    }

    public int getStarti() {
        return starti;
    }

    public int getStarts() {
        return starts;
    }

    public int getEndy() {
        return endy;
    }

    public int getEndm() {
        return endm;
    }

    public int getEndd() {
        return endd;
    }

    public int getEndh() {
        return endh;
    }

    public int getEndi() {
        return endi;
    }

    public int getEnds() {
        return ends;
    }

    public Event(String image, String title, String venue, String desc, String id, int starty, int startm, int startd, int starth, int starti, int starts, int endy, int endm, int endd, int endh, int endi, int ends) {
        this.image = image;
        this.title = title;
        this.venue = venue;
        this.desc = desc;
        this.id = id;
        this.starty = starty;
        this.startm = startm;
        this.startd = startd;
        this.starth = starth;
        this.starti = starti;
        this.starts = starts;
        this.endy = endy;
        this.endm = endm;
        this.endd = endd;
        this.endh = endh;
        this.endi = endi;
        this.ends = ends;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getStartTime() {
        return starty + "-" + startm + "-" + startd + " " + String.format("%02d", starth) + ":" + String.format("%02d", starti) + ":" + String.format("%02d", starts);
    }

    public String getEndTime() {
        return endy + "-" + endm + "-" + endd + " " + String.format("%02d", endh) + ":" + String.format("%02d", endi) + ":" + String.format("%02d", ends);
    }

    public String getDesc() {
        return desc;
    }

    public String getId() {
        return id;
    }
}