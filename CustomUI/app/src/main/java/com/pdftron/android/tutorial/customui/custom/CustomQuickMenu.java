package com.pdftron.android.tutorial.customui.custom;

import android.content.Context;
import androidx.annotation.NonNull;

import com.pdftron.android.tutorial.customui.R;
import com.pdftron.pdf.Annot;
import com.pdftron.pdf.controls.PdfViewCtrlTabFragment;
import com.pdftron.pdf.controls.PdfViewCtrlTabHostFragment;
import com.pdftron.pdf.tools.QuickMenu;
import com.pdftron.pdf.tools.QuickMenuItem;
import com.pdftron.pdf.tools.ToolManager;
import com.pdftron.pdf.utils.CommonToast;

import java.util.ArrayList;

/**
 * Delegate class that adds custom quick menu buttons when annotations are selected. In particular,
 * this sample adds a custom star to almost all of the annotation quick menus, adds a "Link" quick
 * menu button when square annotations are selected, and adds an "UnLink" quick menu button when circle
 * annotations are selected.
 */
public class CustomQuickMenu extends CustomizationDelegate {

    public CustomQuickMenu(@NonNull Context context, @NonNull PdfViewCtrlTabHostFragment tabHostFragment) {
        super(context, tabHostFragment);
    }

    @Override
    public void applyCustomization(@NonNull PdfViewCtrlTabFragment tabFragment) {
        customizeQuickMenu(mContext, tabFragment);
    }

    private static void customizeQuickMenu(@NonNull final Context context,
            @NonNull final PdfViewCtrlTabFragment tabFragment) {
        // let's add some new items in the quick menu
        tabFragment.addQuickMenuListener(new ToolManager.QuickMenuListener() {
            @Override
            public boolean onQuickMenuClicked(QuickMenuItem menuItem) {
                int which = menuItem.getItemId();
                if (which == R.id.qm_star) {
                    CommonToast.showText(context, "Star pressed");
                    return true;
                } else if (which == R.id.qm_custom_link) {
                    CommonToast.showText(context, "Link pressed");
                } else if (which == R.id.qm_custom_unlink) {
                    CommonToast.showText(context, "Unlink pressed");
                }
                return false;
            }

            @Override
            public boolean onShowQuickMenu(QuickMenu quickMenu, Annot annot) {
                // Programmatically change quick menu
                try {
                    if (annot != null && quickMenu != null) {
                        if (annot.getType() == Annot.e_Square) {
                            // Add a custom quick menu button when square annotations are selected
                            // to the first row called "Link" with a ic_link_black_24dp icon
                            QuickMenuItem item =
                                    new QuickMenuItem(context,
                                            R.id.qm_custom_link,
                                            QuickMenuItem.FIRST_ROW_MENU
                                    );
                            item.setTitle(R.string.qm_custom_link);
                            item.setIcon(R.drawable.ic_link_black_24dp);
                            item.setOrder(3);
                            ArrayList<QuickMenuItem> items = new ArrayList<>(1);
                            items.add(item);
                            quickMenu.addMenuEntries(items);
                        } else if (annot.getType() == Annot.e_Circle) {
                            // Add a custom quick menu button when circle annotations are selected
                            // to the overflow called "Unlink"
                            QuickMenuItem item = new QuickMenuItem(context, R.id.qm_custom_unlink, QuickMenuItem.OVERFLOW_ROW_MENU);
                            item.setTitle(R.string.qm_custom_unlink);
                            ArrayList<QuickMenuItem> items = new ArrayList<>(1);
                            items.add(item);
                            quickMenu.addMenuEntries(items);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                return false;
            }

            @Override
            public void onQuickMenuShown() {
                // Called when the quick menu is shown
            }

            @Override
            public void onQuickMenuDismissed() {
                // Called when the quick menu is dismissed
            }
        });
    }
}
