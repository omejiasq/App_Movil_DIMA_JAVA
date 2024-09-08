package co.itfusion.interfaces;

import androidx.annotation.Nullable;

public interface IBlockUI {

    default void showBlockUI(@Nullable String message) {}
    default void hideBlockUI() {}

}
