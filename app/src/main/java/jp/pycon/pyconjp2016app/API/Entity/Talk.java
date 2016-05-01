package jp.pycon.pyconjp2016app.API.Entity;

/**
 * Created by rhoboro on 4/23/16.
 */
public class Talk {
    public String subject;
    public String time;
    public String room;
    public String speaker;
    public String target;
    public String category;

    public String getSubject() {
        return "【サンプル】Sphinxで作る貢献しやすいドキュメント翻訳の仕組み(ja)";
    }

    public String getTime() {
        return "10:00-11:00";
    }

    public String getRoom() {
        return "メインホール";
    }

    public String getSpeaker() {
        return "Shimizukawa (SAMPLE)";
    }

    public String getTarget() {
        return "初級";
    }

    public String getCategory() {
        return "Documentation";
    }
}
