package com.oliverjohnson.mediacollection.datastructures;

import java.io.Serializable;
import java.security.PublicKey;

public class Disc implements Serializable
{
    // Available disc formats
    public enum Format
    {
        Bluray_4K,
        Bluray,
        DVD,
        CD
    }

    // Streaming Quality
    public enum StreamingQuality
    {
        Bluray_4K,
        Bluray_1080p,
        DVD_480p,
        FLAC,
        NotAvailable
    }

    // Disc information fields
    private String title;
    private Format format;
    private Format streamingFormat;
    private StreamingQuality streamingQuality;
    private int boxID;
    private int index;

    private MediaObject mediaObject;

    // Constructor
    public Disc(String title, Format format, Format streamingFormat, StreamingQuality streamingQuality, int boxID, int index, MediaObject mediaObject)
    {
        this.title = title;
        this.format = format;
        this.streamingFormat = streamingFormat;
        this.streamingQuality = streamingQuality;
        this.boxID = boxID;
        this.index = index;
        this.mediaObject = mediaObject;
    }

    public String getTitle() {
        return title;
    }
    public Format getFormat() { return format; }
    public Format getStreamingFormat() { return streamingFormat; }
    public StreamingQuality getStreamingQuality() { return streamingQuality; }
    public int getBoxID() { return boxID; }
    public int getIndex() { return  index; }
    public MediaObject getMediaObject() { return mediaObject; }

    public static String[] getFormatNames()
    {
        // Get formats
        Disc.Format[] formats = Disc.Format.values();
        String[] formatNames = new String[formats.length];

        // Convert to strings
        for(int i=0; i < formats.length; i++)
        {
            formatNames[i] = formats[i].toString();
            formatNames[i] = formatNames[i].replace("bluray", "blu-ray");
            formatNames[i] = formatNames[i].replace("Bluray", "Blu-ray");
            formatNames[i] = formatNames[i].replace("_", " ");
        }

        return formatNames;
    }
}
