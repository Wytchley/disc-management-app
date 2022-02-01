package com.oliverjohnson.mediacollection.datastructures;

import com.oliverjohnson.mediacollection.R;

public class MovieDisc
{
    public int id;
    public String title;
    public int boxID;
    public String boxName;
    public int boxIndex;
    public int formatID;
    public String formatName;

    public int getFormatImageResourceID()
    {
        switch (formatName)
        {
            case "DVD":
                return R.drawable.dvd_logo;
            case "Blu-ray":
                return R.drawable.bluray_logo;
            case "Blu-ray 3D":
                return R.drawable.bluray3d_logo;
            case "4K Blu-ray":
                return R.drawable.bluray4k_logo;

            default:
                return R.drawable.missing_backdrop;
        }
    }
}
