package com.rdproject.waterhub;

import com.rdproject.waterhub.utils.*;
import net.md_5.bungee.api.plugin.*;

import static com.rdproject.waterhub.plugin.LoaderUtil.*;

public class WaterHub extends Plugin {

    @Override
    public void onEnable() {
        ConstantsUtil.plugin = this;
        LoadFeatures();
    }

}