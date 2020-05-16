package hardcorequesting.client.sounds;

import hardcorequesting.HardcoreQuesting;
import hardcorequesting.client.ClientChange;
import hardcorequesting.network.NetworkManager;
import hardcorequesting.quests.QuestingData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class SoundHandler {
    
    private static final String LABEL = "lore";
    private static List<String> paths = new ArrayList<>();
    private static int loreNumber;
    private static boolean loreMusic = false;
    @Environment(EnvType.CLIENT)
    private static SoundInstance loreSound;
    
    private SoundHandler() {
    }
    
    @Environment(EnvType.CLIENT)
    public static boolean loadLoreReading(String path) {
        /*
        loreMusic = false;
        loreNumber = -1;
        
        int index = paths.indexOf(path);
        if (index == -1) {
            if (new File(path + "lore.ogg").exists()) {
                int number = paths.size();
                
                // Add resource pack to discover lore
                Map<?, ?> resourceManagers = ReflectionHelper.getPrivateValue(ReloadableResourceManagerImpl.class, (ReloadableResourceManagerImpl) MinecraftClient.getInstance().getResourceManager(), 2);
                FallbackResourceManager resourceManager = (FallbackResourceManager) resourceManagers.get("hardcorequesting");
                resourceManager.addResourcePack(new LoreResourcePack(new File(path)));
                
                // Add lore file to sound handler
                SoundManager handler = MinecraftClient.getInstance().getSoundManager();
                
                Sound entry = new Sound(LABEL + number, 1.0f, 1.0f, 0, Sound.Type.SOUND_EVENT, true);
                SoundManager.SoundList list = new SoundManager.SoundList(Lists.newArrayList(entry), true, "sub");
//                list.setSoundCategory(SoundCategory.MASTER);

//                entry.setSoundEntryName(LABEL + number);
//                list.getSoundList().add(entry);
                
                Method method = ReflectionHelper.findMethod(net.minecraft.client.audio.SoundHandler.class, "loadSoundResource", "func_147693_a", Identifier.class, SoundList.class);
                if (method == null || handler == null) {
                    return false;
                }
                try {
                    method.invoke(handler, new Identifier(HardcoreQuesting.SOUNDLOC, LABEL + number), list);
                    loreMusic = true;
                    loreNumber = number;
                    paths.add(path);
                    return true;
                } catch (InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        } else {
            loreNumber = index;
            loreMusic = true;
            return true;
        }
        return false;
         */
        return false;
    }
    
    @Environment(EnvType.CLIENT)
    public static void playLoreMusic() {
        loreSound = play(LABEL + loreNumber, 4F, 1F);
    }
    
    
    public static void play(Sounds sound, PlayerEntity player) {
        if (player instanceof ServerPlayerEntity)
            NetworkManager.sendToPlayer(ClientChange.SOUND.build(sound), (ServerPlayerEntity) player);
    }
    
    public static void playToAll(Sounds sound) {
        NetworkManager.sendToAllPlayers(ClientChange.SOUND.build(sound));
    }
    
    @Environment(EnvType.CLIENT)
    private static SoundInstance play(String sound, float volume, float pitch) {
        return play(new Identifier(HardcoreQuesting.SOUNDLOC, sound), volume, pitch);
    }
    
    @Environment(EnvType.CLIENT)
    private static SoundInstance play(Identifier resource, float volume, float pitch) {
        SoundInstance soundObj = new ClientSound(resource, volume, pitch);
        MinecraftClient.getInstance().getSoundManager().play(soundObj);
        return soundObj;
    }
    
    public static void stopLoreMusic() {
        if (isLorePlaying()) {
            new Thread(() ->
            {
                while (isLorePlaying()) {    // Somehow it doesn't stop the sound on closing the book with escape
                    MinecraftClient.getInstance().getSoundManager().stop(loreSound);
                }
                loreSound = null;
            }).start();
        }
    }
    
    public static boolean isLorePlaying() {
        boolean value = loreSound != null && MinecraftClient.getInstance().getSoundManager().isPlaying(loreSound);
        
        if (!value)
            loreSound = null;
        
        return value;
    }
    
    public static boolean hasLoreMusic() {
        return loreMusic;
    }
    
    public static void handleSoundPacket(Sounds sound) {
        play(sound.getSoundName(), 1F, 1F);
    }
    
    public static void triggerFirstLore() {
        NetworkManager.sendToServer(ClientChange.LORE.build(null));
        playLoreMusic();
    }
    
    public static void handleLorePacket(PlayerEntity player) {
        QuestingData.getQuestingData(player).playedLore = true;
    }
}

