package net.liukrast.santa.registry;

import com.google.common.collect.ImmutableList;
import com.simibubi.create.content.logistics.box.PackageStyles;

import java.util.List;

public class SantaPackages {
    private SantaPackages() {}

    public static final List<PackageStyles.PackageStyle> PRESENTS = ImmutableList.<PackageStyles.PackageStyle>builder()
            .add(basic("red"))
            .add(basic("blue"))
            .add(basic("green"))
            .add(basic("yellow"))
            .add(rare("red_tree"))
            .add(rare("green_pois"))
            .build();

    private static PackageStyles.PackageStyle basic(String name) {
        return new PackageStyles.PackageStyle(name, 12, 14, 21f, false);
    }

    private static PackageStyles.PackageStyle rare(String name) {
        return new PackageStyles.PackageStyle(name, 12, 14, 21f, true);
    }
}
