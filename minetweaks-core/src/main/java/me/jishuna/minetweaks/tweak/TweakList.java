package me.jishuna.minetweaks.tweak;

import com.google.common.collect.ImmutableList;
import java.util.Iterator;
import java.util.List;

public class TweakList implements Iterable<Tweak> {

    private List<Tweak> tweaks = ImmutableList.of();

    public void addOrReplace(Tweak tweak) {
        ImmutableList.Builder<Tweak> builder = new ImmutableList.Builder<>();

        for (Tweak existing : this.tweaks) {
            if (!existing.getName().equals(tweak.getName())) {
                builder.add(existing);
            }
        }

        builder.add(tweak);
        this.tweaks = builder.build();
    }

    public void remove(Tweak tweak) {
        ImmutableList.Builder<Tweak> builder = new ImmutableList.Builder<>();

        for (Tweak existing : this.tweaks) {
            if (!existing.getName().equals(tweak.getName())) {
                builder.add(existing);
            }
        }

        this.tweaks = builder.build();
    }

    @Override
    public Iterator<Tweak> iterator() {
        return this.tweaks.iterator();
    }

    public List<Tweak> getTweaks() {
        return this.tweaks;
    }
}
