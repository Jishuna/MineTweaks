package me.jishuna.minetweaks.tweak;

import com.google.common.collect.ImmutableList;
import java.util.Iterator;
import java.util.List;

public class TweakList<T extends Tweak> implements Iterable<T> {

    private List<T> tweaks = ImmutableList.of();

    public void addOrReplace(T tweak) {
        ImmutableList.Builder<T> builder = new ImmutableList.Builder<>();

        for (T existing : this.tweaks) {
            if (!existing.getName().equals(tweak.getName())) {
                builder.add(existing);
            }
        }

        builder.add(tweak);
        this.tweaks = builder.build();
    }

    public void remove(T tweak) {
        ImmutableList.Builder<T> builder = new ImmutableList.Builder<>();

        for (T existing : this.tweaks) {
            if (!existing.getName().equals(tweak.getName())) {
                builder.add(existing);
            }
        }

        this.tweaks = builder.build();
    }

    @Override
    public Iterator<T> iterator() {
        return this.tweaks.iterator();
    }

    public List<T> getTweaks() {
        return this.tweaks;
    }
}
