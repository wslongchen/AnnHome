package entity;

import java.io.Serializable;

/**
 * Created by mrpan on 15/11/17.
 */
public class Images extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Img full;
    private Img thumbnail;

    public Img getMedium() {
        return medium;
    }

    public void setMedium(Img medium) {
        this.medium = medium;
    }

    public Img getLarge() {
        return large;
    }

    public void setLarge(Img large) {
        this.large = large;
    }

    public Img getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Img thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Img getFull() {
        return full;
    }

    public void setFull(Img full) {
        this.full = full;
    }

    private Img medium;
    private Img large;
}
