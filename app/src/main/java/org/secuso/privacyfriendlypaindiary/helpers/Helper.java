/*
    This file is part of Privacy Friendly Pain Diary.

    Privacy Friendly Pain Diary is free software: you can redistribute it
    and/or modify it under the terms of the GNU General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program. If not, see <http://www.gnu.org/licenses/>.
*/
package org.secuso.privacyfriendlypaindiary.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.secuso.privacyfriendlypaindiary.R;
import org.secuso.privacyfriendlypaindiary.database.entities.enums.BodyRegion;
import org.secuso.privacyfriendlypaindiary.database.entities.enums.Condition;
import org.secuso.privacyfriendlypaindiary.database.entities.enums.PainQuality;
import org.secuso.privacyfriendlypaindiary.database.entities.enums.Time;
import org.secuso.privacyfriendlypaindiary.database.entities.interfaces.DiaryEntryInterface;
import org.secuso.privacyfriendlypaindiary.database.entities.interfaces.DrugIntakeInterface;
import org.secuso.privacyfriendlypaindiary.database.entities.interfaces.PainDescriptionInterface;

import java.text.SimpleDateFormat;
import java.util.EnumSet;

/**
 * @author Susanne Felsen
 * @version 20180208
 */
public class Helper {

    public static int getResourceIDForBodyRegion(BodyRegion bodyRegion) {
        switch(bodyRegion) {
            case ABDOMEN_RIGHT:
                return R.drawable.paindiary_person_abdomen_right;
            case ABDOMEN_LEFT:
                return R.drawable.paindiary_person_abdomen_left;
            case GROIN_LEFT:
                return R.drawable.paindiary_person_groin_left;
            case GROIN_RIGHT:
                return R.drawable.paindiary_person_groin_right;
            case THIGH_LEFT:
                return R.drawable.paindiary_person_thigh_left;
            case THIGH_RIGHT:
                return R.drawable.paindiary_person_thigh_right;
            case KNEE_LEFT:
                return R.drawable.paindiary_person_knee_left;
            case KNEE_RIGHT:
                return R.drawable.paindiary_person_knee_right;
            case LOWER_LEG_LEFT:
                return R.drawable.paindiary_person_leg_left;
            case LOWER_LEG_RIGHT:
                return R.drawable.paindiary_person_leg_right;
            case FOOT_LEFT:
                return R.drawable.paindiary_person_foot_left;
            case FOOT_RIGHT:
                return R.drawable.paindiary_person_foot_right;
            case CHEST_LEFT:
                return R.drawable.paindiary_person_chest_left;
            case CHEST_RIGHT:
                return R.drawable.paindiary_person_chest_right;
            case NECK:
                return R.drawable.paindiary_person_neck;
            case HEAD:
                return R.drawable.paindiary_person_head;
            case UPPER_ARM_LEFT:
                return R.drawable.paindiary_person_upperarm_left;
            case UPPER_ARM_RIGHT:
                return R.drawable.paindiary_person_upperarm_right;
            case LOWER_ARM_LEFT:
                return R.drawable.paindiary_person_lowerarm_left;
            case LOWER_ARM_RIGHT:
                return R.drawable.paindiary_person_lowerarm_right;
            case HAND_LEFT:
                return R.drawable.paindiary_person_hand_left;
            case HAND_RIGHT:
                return R.drawable.paindiary_person_hand_right;
            default:
                return 0;
        }
    }

    public static int getResourceIDForCondition(Condition condition) {
        switch(condition) {
            case VERY_BAD:
                return R.drawable.ic_sentiment_very_dissatisfied;
            case BAD:
                return R.drawable.ic_sentiment_dissatisfied;
            case OKAY:
                return R.drawable.ic_sentiment_neutral;
            case GOOD:
                return R.drawable.ic_sentiment_satisfied;
            case VERY_GOOD:
                return R.drawable.ic_sentiment_very_satisfied;
            default:
                return R.drawable.ic_sentiment_neutral;
        }
    }

    public static Bitmap overlay(Bitmap[] images) {
        Bitmap overlay = Bitmap.createBitmap(images[0].getWidth(), images[0].getHeight(), images[0].getConfig());
        Canvas canvas = new Canvas(overlay);
        for(int i = 0; i < images.length; i++) {
            canvas.drawBitmap(images[i], 0, 0, null);
        }
        return overlay;
    }

    public static View getDiaryEntrySummary(Context context, DiaryEntryInterface diaryEntry) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.diaryentry_summary, null);

        PainDescriptionInterface painDescription = diaryEntry.getPainDescription();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        ((TextView) view.findViewById(R.id.date)).setText(dateFormat.format(diaryEntry.getDate()));
        if(diaryEntry.getNotes() != null) {
            ((TextView) view.findViewById(R.id.notes_value)).setText(diaryEntry.getNotes());
        }
        if(diaryEntry.getCondition() != null) {
            ((ImageView) view.findViewById(R.id.condition_icon)).setImageResource(getResourceIDForCondition(diaryEntry.getCondition()));
        }
        if(painDescription != null) {
            ((TextView) view.findViewById(R.id.painlevel_value)).setText(Integer.toString(painDescription.getPainLevel()));
            EnumSet<BodyRegion> bodyRegions = painDescription.getBodyRegions();
            if(!bodyRegions.isEmpty()) {
                Bitmap[] images = getBitmapArrayForBodyRegions(context, bodyRegions);
                ((ImageView) view.findViewById(R.id.bodyregion_value)).setImageBitmap(Helper.overlay(images));
                view.findViewById(R.id.bodyregion_value).setVisibility(View.VISIBLE);
            }
            String painQualities = convertPainQualityEnumSetToString(context, painDescription.getPainQualities());
            if(painQualities != null) {
                ((TextView) view.findViewById(R.id.painquality_value)).setText(painQualities);
            }
            String timesOfPain = convertTimeEnumSetToString(context, painDescription.getTimesOfPain());
            if(timesOfPain != null) {
                ((TextView) view.findViewById(R.id.timeofpain_value)).setText(timesOfPain);
            }
        }
        String medication = "";
        for(DrugIntakeInterface drugIntake : diaryEntry.getDrugIntakes()) {
            medication = medication + drugIntake.getDrug().getName() + " (" + drugIntake.getDrug().getDose() + ") " +
                    drugIntake.getQuantityMorning() + " " + drugIntake.getQuantityNoon() + " " + drugIntake.getQuantityEvening() + " " + drugIntake.getQuantityNight() +
                    System.getProperty("line.separator");
        }
        if(!medication.isEmpty()) {
            ((TextView) view.findViewById(R.id.medication_value)).setText(medication);
        }

        return view;
    }

    public static Bitmap[] getBitmapArrayForBodyRegions(Context context, EnumSet<BodyRegion> bodyRegions) {
        Bitmap[] images = new Bitmap[bodyRegions.size()];
        int i = 0;
        for(BodyRegion region : bodyRegions) {
            int resourceID = Helper.getResourceIDForBodyRegion(region);
            if (resourceID != 0) {
                images[i] = BitmapFactory.decodeResource(context.getResources(), resourceID);
                i++;
            }
        }
        return images;
    }

    public static String convertPainQualityEnumSetToString(Context context, EnumSet<PainQuality> painQualities) {
        String painQualitiesAsString = "";
        for(PainQuality quality : painQualities) {
            painQualitiesAsString += context.getString(quality.getResourceID()) + ", ";
        }
        if(!painQualitiesAsString.isEmpty()) {
            painQualitiesAsString = painQualitiesAsString.substring(0, painQualitiesAsString.length() - 2);
        } else {
            painQualitiesAsString = null;
        }
        return painQualitiesAsString;
    }

    public static String convertTimeEnumSetToString(Context context, EnumSet<Time> times) {
        String timesAsString = "";
        for(Time time : times) {
            timesAsString += context.getString(time.getResourceID()) + ", ";
        }
        if(!timesAsString.isEmpty()) {
            timesAsString = timesAsString.substring(0, timesAsString.length() - 2);
        } else {
            timesAsString = null;
        }
        return timesAsString;
    }

}