package me.jishuna.minetweaks.tweak.mob.head;

import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.DyeColor;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Frog;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Llama;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.Villager.Type;
import me.jishuna.jishlib.entity.AxolotlIdentifier;
import me.jishuna.jishlib.entity.CatIdentifier;
import me.jishuna.jishlib.entity.ColoredEntityIdentifier;
import me.jishuna.jishlib.entity.EntityIdentifier;
import me.jishuna.jishlib.entity.FoxIdentifier;
import me.jishuna.jishlib.entity.FrogIdentifier;
import me.jishuna.jishlib.entity.HorseIdentifier;
import me.jishuna.jishlib.entity.LlamaIdentifier;
import me.jishuna.jishlib.entity.MooshroomIdentifier;
import me.jishuna.jishlib.entity.PandaIdentifier;
import me.jishuna.jishlib.entity.ParrotIdentifier;
import me.jishuna.jishlib.entity.RabbitIdentifier;
import me.jishuna.jishlib.entity.VillagerIdentifier;
import me.jishuna.jishlib.util.StringUtils;

public final class MobHeads {

    private static final String VANILLA_HEAD = "None (Vanilla Head)";

    private static final String GENERIC_TAIGA_ZOMBIE = "d58698448e316d4bdd833d9621c2db547b60cf5a5566a79f296fd7cc91e918d9";
    private static final String GENERIC_SWAMP_ZOMBIE = "2a6e186a2b3d5d427172b74dbd4dacaf287e7e8ffd9bbd1a47ac4ece506ad2ff";
    private static final String GENERIC_SNOW_ZOMBIE = "5de1fbe711a6812e53fd56280a8f7d9c3a5c9506678fdef8893418b0efac590a";
    private static final String GENERIC_SAVANNA_ZOMBIE = "d680ed3fa41eff171b30fee52452d3b89284957af5c5fd4aa61b8bf1f6f6bd2f";
    private static final String GENERIC_DESERT_ZOMBIE = "41225ae82e4918eb84f4687fe97a83b291515e7a56e55499c8b046aed2d6e182";
    private static final String GENERIC_ZOMBIE_VILLAGER = "c45c11e0327035649ca0600ef938900e25fd1e38017422bc9740e4cda2cba892";

    private static final String GENERIC_ZOMBIE_CLERIC = "568001f4f7d071b254be7f31205361ae4fab63ce3886471aa1c74e65f65e1e69";

    public static Map<EntityIdentifier, MobHeadData> getDefaultHeads() {
        Map<EntityIdentifier, MobHeadData> map = new LinkedHashMap<>();

        addHead(map, new EntityIdentifier(EntityType.ALLAY), "e1c59dccde4b8535500dcf6794ca450663f607290e2510f6d8eb1e5eb71da5af");
        addHead(map, new AxolotlIdentifier(Axolotl.Variant.BLUE), "3e68dc59dbe767a5283164e3c94acc14badd34a857cec1a6c9f26a28ced29c83");
        addHead(map, new AxolotlIdentifier(Axolotl.Variant.CYAN), "d543bfa4ea2334667b6abfe40d852ecf55f8506a9c0c5ad8dd1e732f58d9c6c3");
        addHead(map, new AxolotlIdentifier(Axolotl.Variant.GOLD), "e17f8e58adf8e1c5ede450d2235cff612fc16c4b74490a0e7dbd5930b8c83e7f");
        addHead(map, new AxolotlIdentifier(Axolotl.Variant.LUCY), "7b910fbc216f724d29655155b2a3858a80f234a0cfed609e22fc670683ab777a");
        addHead(map, new AxolotlIdentifier(Axolotl.Variant.WILD), "8db22a0ea62d5d22867d852d01d396177b7a0d63e18cdc5e29ab39f5249c5074");
        addHead(map, new EntityIdentifier(EntityType.BAT), "3820a10db222f69ac2215d7d10dca47eeafa215553764a2b81bafd479e7933d1");
        addHead(map, new EntityIdentifier(EntityType.BEE), "4420c9c43e095880dcd2e281c81f47b163b478f58a584bb61f93e6e10a155f31");
        addHead(map, new EntityIdentifier(EntityType.BLAZE), "b20657e24b56e1b2f8fc219da1de788c0c24f36388b1a409d0cd2d8dba44aa3b");
        addHead(map, new EntityIdentifier(EntityType.CAMEL), "ba4c95bfa0b61722255389141b505cf1a38bad9b0ef543de619f0cc9221ed974");
        addHead(map, new EntityIdentifier(EntityType.CAMEL), "ba4c95bfa0b61722255389141b505cf1a38bad9b0ef543de619f0cc9221ed974");
        addHead(map, new CatIdentifier(Cat.Type.ALL_BLACK), "61b528d57201344eb5faa8d5651fba320c6ddbe4dca8527f38f3786a689e95c7");
        addHead(map, new CatIdentifier(Cat.Type.BLACK), "2e2d7c6decde6cfd9c213bc453e836dab908e3579458a0ea0ee17b72757f927d");
        addHead(map, new CatIdentifier(Cat.Type.BRITISH_SHORTHAIR), "3c2f986592eb92394ef027372bf03a0ec397177ae42b47f5e69939dd98446d59");
        addHead(map, new CatIdentifier(Cat.Type.CALICO), "a028e3c6f4350b3e424b372f3c946f57b783b9aa0e4e29891ff578441ef01308");
        addHead(map, new CatIdentifier(Cat.Type.JELLIE), "288394b84443fbc2a601c444632848d414afa693087e7cb00b37ab6312940938");
        addHead(map, new CatIdentifier(Cat.Type.PERSIAN), "e7db3694b8804c898222b1b12879e79f6165502cf2a8253942d23aad362dd4ce");
        addHead(map, new CatIdentifier(Cat.Type.RAGDOLL), "1e44735e847e71051498bfd95fb992fa4497a50de1d4e3b59903b2e561f8b40c");
        addHead(map, new CatIdentifier(Cat.Type.RED), "9ff4915e3e5967381d43a8b47f3c3b49510fa2b99e9f7e69dc3b8f5d0ba6fa6f");
        addHead(map, new CatIdentifier(Cat.Type.SIAMESE), "6c24576b6ce08b049bfaf6bf8397fc5d3d838ff32be93dfdbcb8caa2b316b2b");
        addHead(map, new CatIdentifier(Cat.Type.TABBY), "f425e04f91dd768f6050b2ad477e642eece104f5e9c3afce4f7547dda1f25621");
        addHead(map, new CatIdentifier(Cat.Type.WHITE), "48e024690469f34fbbe4b3a494068cf09316f02765fade164f4885a0aed60855");
        addHead(map, new EntityIdentifier(EntityType.CAVE_SPIDER), "eccc4a32d45d74e8b14ef1ffd55cd5f381a06d4999081d52eaea12e13293e209");
        addHead(map, new EntityIdentifier(EntityType.CHICKEN), "ca3582ce4889333dad329e4e24372a03a5daa2c34280c56256af5283edb043f8");
        addHead(map, new EntityIdentifier(EntityType.COD), "7892d7dd6aadf35f86da27fb63da4edda211df96d2829f691462a4fb1cab0");
        addHead(map, new EntityIdentifier(EntityType.COW), "be8456155142cbe4e61353ffbaff304d3d9c4bc9247fc27b92e33e6e26067edd");
        addHead(map, new EntityIdentifier(EntityType.CREEPER), VANILLA_HEAD);
        addHead(map, new EntityIdentifier(EntityType.DOLPHIN), "8e9688b950d880b55b7aa2cfcd76e5a0fa94aac6d16f78e833f7443ea29fed3");
        addHead(map, new EntityIdentifier(EntityType.DONKEY), "399bb50d1a214c394917e25bb3f2e20698bf98ca703e4cc08b42462df309d6e6");
        addHead(map, new EntityIdentifier(EntityType.DROWNED), "c84df79c49104b198cdad6d99fd0d0bcf1531c92d4ab6269e40b7d3cbbb8e98c");
        addHead(map, new EntityIdentifier(EntityType.ELDER_GUARDIAN), "e92089618435a0ef63e95ee95a92b83073f8c33fa77dc5365199bad33b6256");
        addHead(map, new EntityIdentifier(EntityType.ENDERMAN), "7a59bb0a7a32965b3d90d8eafa899d1835f424509eadd4e6b709ada50b9cf");
        addHead(map, new EntityIdentifier(EntityType.ENDERMITE), "5bc7b9d36fb92b6bf292be73d32c6c5b0ecc25b44323a541fae1f1e67e393a3e");
        addHead(map, new EntityIdentifier(EntityType.EVOKER), "e79f133a85fe00d3cf252a04d6f2eb2521fe299c08e0d8b7edbf962740a23909");
        addHead(map, new FoxIdentifier(Fox.Type.RED), "d8954a42e69e0881ae6d24d4281459c144a0d5a968aed35d6d3d73a3c65d26a");
        addHead(map, new FoxIdentifier(Fox.Type.SNOW), "41436377eb4c4b4e39fb0e1ed8899fb61ee1814a9169b8d08729ef01dc85d1ba");
        addHead(map, new FrogIdentifier(Frog.Variant.COLD), "ce62e8a048d040eb0533ba26a866cd9c2d0928c931c50b4482ac3a3261fab6f0");
        addHead(map, new FrogIdentifier(Frog.Variant.TEMPERATE), "23ce6f9998ed2da757d1e6372f04efa20e57dfc17c3a06478657bbdf51c2f2a2");
        addHead(map, new FrogIdentifier(Frog.Variant.WARM), "f77314fa038ec31357845a93274b4dc884124686728ffe0ded9c35466aca0aab");
        addHead(map, new EntityIdentifier(EntityType.GHAST), "8b6a72138d69fbbd2fea3fa251cabd87152e4f1c97e5f986bf685571db3cc0");
        addHead(map, new EntityIdentifier(EntityType.GLOW_SQUID), "45c999dd12dd1c866fdd0ee94a3973533428cd72d9296c62724f429365da8eeb");
        addHead(map, new EntityIdentifier(EntityType.GOAT), "a662336d8ae092407e58f7cc80d20f20e7650357a454ce16e3307619a0110648");
        addHead(map, new EntityIdentifier(EntityType.GUARDIAN), "a0bf34a71e7715b6ba52d5dd1bae5cb85f773dc9b0d457b4bfc5f9dd3cc7c94");
        addHead(map, new EntityIdentifier(EntityType.HOGLIN), "9bb9bc0f01dbd762a08d9e77c08069ed7c95364aa30ca1072208561b730e8d75");
        addAllStyles(map, Horse.Color.BLACK, "3efb0b9857d7c8d295f6df97b605f40b9d07ebe128a6783d1fa3e1bc6e44117");
        addAllStyles(map, Horse.Color.BROWN, "25e397def0af06feef22421860088186639732aa0a5eb5756e0aa6b03fd092c8");
        addAllStyles(map, Horse.Color.CHESTNUT, "9717d71025f7a62c90a333c51663ffeb385a9a0d92af68083c5b045c0524b23f");
        addAllStyles(map, Horse.Color.CREAMY, "a6dae0ade0e0dafb6dbc7786ce4241242b6b6df527a0f7af0a42184c93fd646b");
        addAllStyles(map, Horse.Color.DARK_BROWN, "156b7bc1a4836eb428ea8925eceb5e01dfbd30c7deff6c9482689823203cfd2f");
        addAllStyles(map, Horse.Color.GRAY, "8f0d955889b0378d4933c956398567e770103ae9eff0f702d0d53d52e7f6a83b");
        addAllStyles(map, Horse.Color.WHITE, "9f4bdd59d4f8f1d5782e0fee4bd64aed100627f188a91489ba37eeadededd827");
        addHead(map, new EntityIdentifier(EntityType.HUSK), "d48a5a5e4df90528dba35e0667cdc0a7ddc025740a2b19bf355a68ab899a2fe7");
        addHead(map, new EntityIdentifier(EntityType.ILLUSIONER), "4639d325f4494258a473a93a3b47f34a0c51b3fceaf59fee87205a5e7ff31f68");
        addHead(map, new EntityIdentifier(EntityType.IRON_GOLEM), "e13f34227283796bc017244cb46557d64bd562fa9dab0e12af5d23ad699cf697");
        addHead(map, new LlamaIdentifier(EntityType.LLAMA, Llama.Color.BROWN), "7f832466dcc7d5e7702cdee4cd555dbd39637d20adf9367fb03cfd6888baaae7");
        addHead(map, new LlamaIdentifier(EntityType.LLAMA, Llama.Color.CREAMY), "bae25ddc2d2539c565dff2aa5006033f14cc06379fe28b0731c7bdc65ba0e016");
        addHead(map, new LlamaIdentifier(EntityType.LLAMA, Llama.Color.GRAY), "6d2ffce9a174fe1c084e2d82052182d94f95ed436b75ff7ea7a4e94d94c72d8a");
        addHead(map, new LlamaIdentifier(EntityType.LLAMA, Llama.Color.WHITE), "de703ab031ed66622f12957ef59a8b5c8a269cebd18f9326248b68c3bbe20163");
        addHead(map, new EntityIdentifier(EntityType.MAGMA_CUBE), "a1c97a06efde04d00287bf20416404ab2103e10f08623087e1b0c1264a1c0f0c");
        addHead(map, new EntityIdentifier(EntityType.MULE), "46dcda265e57e4f51b145aacbf5b59bdc6099ffd3cce0a661b2c0065d80930d8");
        addHead(map, new MooshroomIdentifier(MushroomCow.Variant.BROWN), "199cd80c0a353b181b6588e9d820671c59ed9f27f1cfcd2195e65b918fb65e47");
        addHead(map, new MooshroomIdentifier(MushroomCow.Variant.RED), "767ac842a8d12c02d8a9f0d803eda918dc4d0c80e0f2ea02b4b9a7581cd7a4b5");
        addHead(map, new EntityIdentifier(EntityType.OCELOT), "51f07e3f2e5f256bfade666a8de1b5d30252c95e98f8a8ecc6e3c7b7f67095");
        addHead(map, new PandaIdentifier(Panda.Gene.AGGRESSIVE), "5880e236494e7135db8ec45f64ba9249cadb94a5c1a7a5157f3e02b01bfdb0f6");
        addHead(map, new PandaIdentifier(Panda.Gene.BROWN), "b4f7c73fda6a34cf8be4c7907dd0f5f0865dd77fd882fc633563649c57517cae");
        addHead(map, new PandaIdentifier(Panda.Gene.LAZY), "962a024e871bfb2eb995dad21e9e70489043d3cbc73d7fa5520aeb765993347");
        addHead(map, new PandaIdentifier(Panda.Gene.NORMAL), "ba6e3ad823f96d4a80a14556d8c9c7632163bbd2a876c0118b458925d87a5513");
        addHead(map, new PandaIdentifier(Panda.Gene.PLAYFUL), "9fc1527246fda3e83112534414bdbcc3c10f3ba5cf08e47af48a18e76ee148fd");
        addHead(map, new PandaIdentifier(Panda.Gene.WEAK), "d67219a46d3957e3292b81d2e28f86c63501ecb6673ace326035b5229bd8db4a");
        addHead(map, new PandaIdentifier(Panda.Gene.WORRIED), "2266672e4ba54c5c0be59e0461a6e32ea0a7cf115d711867d2922ee9ca523690");
        addHead(map, new ParrotIdentifier(Parrot.Variant.BLUE), "20e03b10c15ee5601423867dfb8bcbcbc919ca96c0eea63073ec8e795eabd05f");
        addHead(map, new ParrotIdentifier(Parrot.Variant.CYAN), "bc6471f23547b2dbdf60347ea128f8eb2baa6a79b0401724f23bd4e2564a2b61");
        addHead(map, new ParrotIdentifier(Parrot.Variant.GRAY), "a3c34722ac64496c9b84d0c54019daae6185d6094990133ad6810eea3d24067a");
        addHead(map, new ParrotIdentifier(Parrot.Variant.GREEN), "5fc9a3b9d5879c2150984dbfe588cc2e61fb1de1e60fd2a469f69dd4b6f6a993");
        addHead(map, new ParrotIdentifier(Parrot.Variant.RED), "5d1a168bc72cb314f7c86feef9d9bc7612365244ce67f0a104fce04203430c1d");
        addHead(map, new EntityIdentifier(EntityType.PHANTOM), "7e95153ec23284b283f00d19d29756f244313a061b70ac03b97d236ee57bd982");
        addHead(map, new EntityIdentifier(EntityType.PIG), "ff218ef4de0005eddfb77ac5d4ef52728779b5e4d747fba3b79a39a43b2fd0c4");
        addHead(map, new EntityIdentifier(EntityType.PIGLIN), VANILLA_HEAD);
        addHead(map, new EntityIdentifier(EntityType.PIGLIN_BRUTE), "3e300e9027349c4907497438bac29e3a4c87a848c50b34c21242727b57f4e1cf");
        addHead(map, new EntityIdentifier(EntityType.PILLAGER), "4aee6bb37cbfc92b0d86db5ada4790c64ff4468d68b84942fde04405e8ef5333");
        addHead(map, new EntityIdentifier(EntityType.POLAR_BEAR), "c4fe926922fbb406f343b34a10bb98992cee4410137d3f88099427b22de3ab90");
        addHead(map, new EntityIdentifier(EntityType.PUFFERFISH), "17152876bc3a96dd2a2299245edb3beef647c8a56ac8853a687c3e7b5d8bb");
        addHead(map, new RabbitIdentifier(Rabbit.Type.BLACK), "19a675edb3cba0f3436ae9473cf03592b7a49d38813579084d637e7659999b8e");
        addHead(map, new RabbitIdentifier(Rabbit.Type.BLACK_AND_WHITE), "32f39e0a603386ca1ee36236e0b490a1547e6e2a89911674509037fb6f711810");
        addHead(map, new RabbitIdentifier(Rabbit.Type.BROWN), "c1db38ef3c1a1d59f779a0cd9f9e616de0cc9acc7734b8facc36fc4ea40d0235");
        addHead(map, new RabbitIdentifier(Rabbit.Type.GOLD), "2a6361fea24b111ed78c1fefc295212e8a59b0c88b656062527b17a2d7489c81");
        addHead(map, new RabbitIdentifier(Rabbit.Type.SALT_AND_PEPPER), "cc4349fe9902dd76c1361f8d6a1f79bff6f433f3b7b18a47058f0aa16b9053f");
        addHead(map, new RabbitIdentifier(Rabbit.Type.THE_KILLER_BUNNY), "a0dcddc236972edcd48e825b6b0054b7b6e1a781e6f12ae04c14a07827ca8dcc");
        addHead(map, new RabbitIdentifier(Rabbit.Type.WHITE), "a0dcddc236972edcd48e825b6b0054b7b6e1a781e6f12ae04c14a07827ca8dcc");
        addHead(map, new EntityIdentifier(EntityType.RAVAGER), "5c73e16fa2926899cf18434360e2144f84ef1eb981f996148912148dd87e0b2a");
        addHead(map, new EntityIdentifier(EntityType.SALMON), "8aeb21a25e46806ce8537fbd6668281cf176ceafe95af90e94a5fd84924878");
        addSheep(map);
        addShulkers(map);
        addHead(map, new EntityIdentifier(EntityType.SILVERFISH), "da91dab8391af5fda54acd2c0b18fbd819b865e1a8f1d623813fa761e924540");
        addHead(map, new EntityIdentifier(EntityType.SKELETON), VANILLA_HEAD);
        addHead(map, new EntityIdentifier(EntityType.SKELETON_HORSE), "47effce35132c86ff72bcae77dfbb1d22587e94df3cbc2570ed17cf8973a");
        addHead(map, new EntityIdentifier(EntityType.SLIME), "61affd31efc37ba84f50187394d8688344ccd06cdc926ddfcf2df116986dca9");
        addHead(map, new EntityIdentifier(EntityType.SNIFFER), "87ad920a66e38cc3426a5bff084667e8772116915e298098567c139f222e2c42");
        addHead(map, new EntityIdentifier(EntityType.SNOWMAN), "1fdfd1f7538c040258be7a91446da89ed845cc5ef728eb5e690543378fcf4");
        addHead(map, new EntityIdentifier(EntityType.SPIDER), "35e248da2e108f09813a6b848a0fcef111300978180eda41d3d1a7a8e4dba3c3");
        addHead(map, new EntityIdentifier(EntityType.SQUID), "1f27c6e2c48a390c7e8bfdadfa41b52731bb0eccf7075ca878fe9b00cc242d5d");
        addHead(map, new EntityIdentifier(EntityType.STRAY), "6572747a639d2240feeae5c81c6874e6ee7547b599e74546490dc75fa2089186");
        addHead(map, new EntityIdentifier(EntityType.STRIDER), "406b3ae64ff87afc9ac9e3967031fadbbcd661426060ae1ffc2a52a5417c95b9");
        addHead(map, new EntityIdentifier(EntityType.TADPOLE), "987035f5352334c2cba6ac4c65c2b9059739d6d0e839c1dd98d75d2e77957847");
        addHead(map, new LlamaIdentifier(EntityType.TRADER_LLAMA, Llama.Color.BROWN), "5a4eed85697c78f462c4eb5653b05b76576c1178f704f3c5676f505d8f3983b4");
        addHead(map, new LlamaIdentifier(EntityType.TRADER_LLAMA, Llama.Color.CREAMY), "56307f42fc88ebc211e04ea2bb4d247b7428b711df9a4e0c6d1b921589e443a1");
        addHead(map, new LlamaIdentifier(EntityType.TRADER_LLAMA, Llama.Color.GRAY), "20fdfa60c624fb667c8313b2fb1dab40e0ad2e6e469b567bf596ad26392319c5");
        addHead(map, new LlamaIdentifier(EntityType.TRADER_LLAMA, Llama.Color.WHITE), "15ad6b69cc6b4769d3516a0ce98b99b2a5d406fea4912dec570ea4a4f2bcc0ff");
        addHead(map, new EntityIdentifier(EntityType.TROPICAL_FISH), "d6dd5e6addb56acbc694ea4ba5923b1b25688178feffa72290299e2505c97281");
        addHead(map, new EntityIdentifier(EntityType.TURTLE), "0a4050e7aacc4539202658fdc339dd182d7e322f9fbcc4d5f99b5718a");
        addHead(map, new EntityIdentifier(EntityType.VEX), "869a7c6f0a7358c594c29d3d42cf7b69638ef3b5b3ec1f9d38150c8b2bff7813");
        addVillagers(map);
        addHead(map, new EntityIdentifier(EntityType.VINDICATOR), "e79f133a85fe00d3cf252a04d6f2eb2521fe299c08e0d8b7edbf962740a23909");
        addHead(map, new EntityIdentifier(EntityType.WANDERING_TRADER), "ee011aac817259f2b48da3e5ef266094703866608b3d7d1754432bf249cd2234");
        addHead(map, new EntityIdentifier(EntityType.WARDEN), "bc9c84349742164a22971ee54516fff91d868da72cdcce62069db128c42154b2");
        addHead(map, new EntityIdentifier(EntityType.WITCH), "7e71a6eb303ab7e6f70ed54df9146a80eadf396417cee9495773ffbebfad887c");
        addHead(map, new EntityIdentifier(EntityType.WITHER), "ee280cefe946911ea90e87ded1b3e18330c63a23af5129dfcfe9a8e166588041");
        addHead(map, new EntityIdentifier(EntityType.WOLF), "d0498de6f5b09e0ce35a7292fe50b79fce9065d9be8e2a87c7a13566efb26d72");
        addHead(map, new EntityIdentifier(EntityType.ZOGLIN), "c19b7b5e9ffd4e22b890ab778b4795b662faff2b4978bf815574e48b0e52b301");
        addHead(map, new EntityIdentifier(EntityType.ZOMBIE), VANILLA_HEAD);
        addHead(map, new EntityIdentifier(EntityType.ZOMBIE_HORSE), "d22950f2d3efddb18de86f8f55ac518dce73f12a6e0f8636d551d8eb480ceec");
        addZombieVillagers(map);
        addHead(map, new EntityIdentifier(EntityType.ZOMBIFIED_PIGLIN), "e935842af769380f78e8b8a88d1ea6ca2807c1e5693c2cf797456620833e936f");

        return map;
    }

    private static void addHead(Map<EntityIdentifier, MobHeadData> map, EntityIdentifier identifier, String texture) {
        map.put(identifier, new MobHeadData(StringUtils.formatObject(identifier.getType().getKey().getKey()) + " Head", texture));
    }

    private static void addAllStyles(Map<EntityIdentifier, MobHeadData> map, Horse.Color color, String texture) {
        for (Horse.Style style : Horse.Style.values()) {
            addHead(map, new HorseIdentifier(color, style), texture);
        }
    }

    private static void addSheep(Map<EntityIdentifier, MobHeadData> map) {
        addHead(map, new ColoredEntityIdentifier(EntityType.SHEEP, DyeColor.WHITE), "292df216ecd27624ac771bacfbfe006e1ed84a79e9270be0f88e9c8791d1ece4");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHEEP, DyeColor.LIGHT_GRAY), "74a59be620ae8b3ee0dd0fa22c80affed4a0f729295cb8c41e78ee783f4633ad");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHEEP, DyeColor.GRAY), "e6c2a2755b20ddff551a6903f2dc7e61f13ebe39b1d5ca929c87bd8583ec801f");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHEEP, DyeColor.BLACK), "634ac5b398cf7c86e3f6f188a5127d8b283d772bf5885c70e0c130805f069950");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHEEP, DyeColor.BROWN), "e5813715c2f34f05649f8fa3eaaa67f1eda5e6f9cf930fa9c2e0412d1f9728e1");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHEEP, DyeColor.RED), "e0ce5b5ca9165ac77a9c3e3f64df0d3170d5afcf9d5a5575e3f0c0f21e43b83");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHEEP, DyeColor.ORANGE), "4271442d8a37db49f02a94c29352694962b5d0bd6bea05f1d93fe19eb4e7060e");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHEEP, DyeColor.YELLOW), "12a5354c230e861aac72734a4582d1317026454b807ac353fc3a0bd0d8c422ba");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHEEP, DyeColor.LIME), "1ce4090e1bccf992b36def74a6d7d3972c17db1b75554e2c509271680b8e7974");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHEEP, DyeColor.GREEN), "5753a8ec32be9c550d1c560acb941edd9e3b73ddbf1586923fb37b220b4553dd");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHEEP, DyeColor.CYAN), "60558387b6658f5e9dcffc719214b603f603c4b04e708b7aabe75bcae91e804c");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHEEP, DyeColor.LIGHT_BLUE), "c8eb0d17479870b3973e8e001b82dcde22efc9d10c90412c6733a0b136564d1f");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHEEP, DyeColor.BLUE), "e39efc4b4eadec48576a5700ec812395510327e5d1e7c108fd8abc7796685aa3");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHEEP, DyeColor.PURPLE), "343cbdae1f20a79281d3a71adf242a35c8cc58562b415f1120bca9d94b76f254");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHEEP, DyeColor.MAGENTA), "fe228b04e9b979a10b70b8db6f3fb199deeb581594a5aa4a7febe948db17228b");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHEEP, DyeColor.PINK), "2e7cf1c58dbb7c3255b94c6043fa8f0d776c134f4d98b81ca31410965f47a25a");
    }

    private static void addShulkers(Map<EntityIdentifier, MobHeadData> map) {
        addHead(map, new ColoredEntityIdentifier(EntityType.SHULKER, null), "537a294f6b7b4ba437e5cb35fb20f46792e7ac0a490a66132a557124ec5f997a");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHULKER, DyeColor.WHITE), "6b94b0acb3177b4cdb017fe31cd5c247262def53bf83381c6c82d72c56ac");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHULKER, DyeColor.LIGHT_GRAY), "bfa17d41ea183bee53d546c7bec4ccf6a54d4f508fde6ebf3e5d388d4cbeacb9");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHULKER, DyeColor.GRAY), "a798432fbeb2bc2d757b1d3c3b3558e6990392dd091ea4ef381b2e019c9462");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHULKER, DyeColor.BLACK), "c5455aaadb2317b5f29e98981aa57f5795705069d8f415c0d68a92a791413b3a");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHULKER, DyeColor.BROWN), "c96d7fb87447ffc054fb109b84d6225d41029b1e6710c7de57f661aefa6f");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHULKER, DyeColor.RED), "59994040433af0f015be4d6968c3d55e044c98dac2c4c6a6ea0efac7a6ddb");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHULKER, DyeColor.ORANGE), "f5711d954bd56236d1a9f99be880c5d38990df6bef72e73f745b04995dbf6");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHULKER, DyeColor.YELLOW), "8cbf5586836b7b342932e1d23efc2490cf59c69accf1e05e9ed576caed8b7877");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHULKER, DyeColor.LIME), "17144957c2c5bbfe447f4b2d36a246ea1b023da4cbd1aa2dbbb15e94981248");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHULKER, DyeColor.GREEN), "c21cdec2cf4ebeef35d58b184b832598bc890a0ae5c2d54ee9be586d0");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHULKER, DyeColor.CYAN), "26f173d4e3e8bd6f9be0bf9b745bd1cc7a29ff836ed2d486c5b99292c85cc");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHULKER, DyeColor.LIGHT_BLUE), "c8eb0d17479870b3973e8e001b82dcde22efc9d10c90412c6733a0b136564d1f");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHULKER, DyeColor.BLUE), "9430966d5cebbd787147699a29743751b3ce4bb814e2db564fe92142d119cd1");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHULKER, DyeColor.PURPLE), "1e73832e272f8844c476846bc424a3432fb698c58e6ef2a9871c7d29aeea7");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHULKER, DyeColor.MAGENTA), "69fe291676c7b3f96f3cdf63c47b53fb45d73bd6fe2ce22de10749eb1426a");
        addHead(map, new ColoredEntityIdentifier(EntityType.SHULKER, DyeColor.PINK), "3f847c12d57fe6555e8f9b47e562ecf1683fb6c35f92ce2ced2ae68de628750");
    }

    private static void addVillagers(Map<EntityIdentifier, MobHeadData> map) {
        // Armorer
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.ARMORER, Type.DESERT), "d0a3e6c961fb4d6efcf4576455fcd27bc78401256fdf39b25488f31984af96ba");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.ARMORER, Type.JUNGLE), "adad6ca8eae505baea8a4f2ecffe719f3155a22d28f82d82d528440f1a77aa14");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.ARMORER, Type.PLAINS), "f522db92f188ebc7713cf35b4cbaed1cfe2642a5986c3bde993f5cfb3727664c");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.ARMORER, Type.SAVANNA), "8b6cd9a9fb4b13f49d25b82a14040c045ff57aa8c279cce20a2503b8184d9b9c");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.ARMORER, Type.SNOW), "edcb21090cd7e97066a1f1dcae778cda032df8af953767d2a04f4a78dcfc2496");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.ARMORER, Type.SWAMP), "ea7abb127b69338a81b87a17b664214ee9fe0437c8b577a0e3674b1c6d0fca77");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.ARMORER, Type.TAIGA), "6fc9fbe1422d5b0edbe0650e511b1d9c2291460752b066c4f701c0297a6201e8");

        // Butcher
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.BUTCHER, Type.DESERT), "4a6b7136db906a2af54fd446094f5d4d074c4625665bce5706ad6208b8b383f9");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.BUTCHER, Type.JUNGLE), "9a9e2421cdc09f2ee2710e80bbea3f64ff35f728cff8defa8d87540efe7966d");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.BUTCHER, Type.PLAINS), "c6774d2df515eceae9eed291c1b40f94adf71df0ab81c7191402e1a45b3a2087");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.BUTCHER, Type.SAVANNA), "366715115de766c949e8700b7650c8994fd3eeb97bd1836c2e16b5d8d5551b5d");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.BUTCHER, Type.SNOW), "7bb5bfa9ac5412e7d116f9d424e0fd3fa40eedc2c3e7c906c055bb5f4c5c587a");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.BUTCHER, Type.SWAMP), "5393370444ee915595630cd900d065c03cc5c61f7a91edd8fec24f204b3eeb4f");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.BUTCHER, Type.TAIGA), "89b921ca9e5e7a43ebb904ca5b833d5c9b37f0fe039e9112303c0f9b188ab739");

        // Cartographer
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.CARTOGRAPHER, Type.DESERT), "a6f25ea1c39a3d73579e5330e04c2b173ba2fd5c0ca3452be5ff1aae083f6328");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.CARTOGRAPHER, Type.JUNGLE), "70880c4367c4162a1dbbb88cd84e52906b18612f1185bf6efe8244fee2939798");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.CARTOGRAPHER, Type.PLAINS), "94248dd0680305ad73b214e8c6b00094e27a4ddd8034676921f905130b858bdb");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.CARTOGRAPHER, Type.SAVANNA), "f2f944222ff693fe088bd543c8efdd3246eab6a34978acde774f7e295dee9e16");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.CARTOGRAPHER, Type.SNOW), "4143895b04cce7b3ad956620bb48d1aaaa6bbdcc10bb3a8c4aa0a55939f050be");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.CARTOGRAPHER, Type.SWAMP), "2e040983328da42b4eda931bdcefb39a8816b32045da1ed9f335e538c18f3c41");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.CARTOGRAPHER, Type.TAIGA), "3e1fb52b7ae82bb091c54c33b7fa6b80c8a0250372b9d1cbd6f796522f6479d");

        // Cleric
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.CLERIC, Type.DESERT), "d24ba760a61dd256c52b325129f46016ae892232a0dea1715f997f7c4d622bef");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.CLERIC, Type.JUNGLE), "86e7a90bb53908c19ccf795e6ea5c19d097b19f1f111fc2f30a61e689da8a4fd");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.CLERIC, Type.PLAINS), "a8856eaafad96d76fa3b5edd0e3b5f45ee49a3067306ad94df9ab3bd5b2d142d");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.CLERIC, Type.SAVANNA), "46cc8fa8379665fbb8c924e45235da1c988c1c523af1b2479796d4a49af1c5c8");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.CLERIC, Type.SNOW), "7e3d3635ce411abf1e4f373d161d07b8c47e359b6c56f74b413cb494ac746e2d");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.CLERIC, Type.SWAMP), "1a8e3e224a768bb5771d6e4653e48a54fe6cd095fc399d3ec39b95c2544af054");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.CLERIC, Type.TAIGA), "4bfad3b0fc8d19a7dda68087cf5a5e6865cc9faf2e79edf10af4bfa70a4d4bd9");

        // Farmer
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.FARMER, Type.DESERT), "355d61a2409eb0b49b3e88b2888467f20a3b06212a10e7b6efb9ce3bc1a0e20f");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.FARMER, Type.JUNGLE), "87f338bcd9fac0c666f2a925cf07d9b0f5fa9f5fd9443f5951ce54ec5b017610");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.FARMER, Type.PLAINS), "55a0b07e36eafdecf059c8cb134a7bf0a167f900966f1099252d903276461cce");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.FARMER, Type.SAVANNA), "c9c94faa7ac9b0752dc7da7386b4d8fc34e2916da5b01789275bbcb7dfce7fcb");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.FARMER, Type.SNOW), "fd95ad3f37bb323785f8d6816763e5a739e2814d611a7ab4afff976f91729faf");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.FARMER, Type.SWAMP), "e2cfc7eade016a969c2b3a87e010a02ac910df60d3714f76184b2c17a703101e");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.FARMER, Type.TAIGA), "608bdb53c55fef32a0658e1c7966614af0bff6091249b8fe3b77a0275da82e43");

        // Fisherman
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.FISHERMAN, Type.DESERT), "74111111e532d68f0e4f913a4e3aedd0c9dfb2847a8aaf1ffa52b3dabcff86ed");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.FISHERMAN, Type.JUNGLE), "d7a9e8b1afe5ee5dd498734cc221c17b71f1de1a6dc8eab2fc88c332ed244c97");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.FISHERMAN, Type.PLAINS), "ac15e5fb56fa16b0747b1bcb05335f55d1fa31561c082b5e3643db5565410852");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.FISHERMAN, Type.SAVANNA), "6acacef5f04bed42e8a808ccec39889e666fb1299e99a9e09060f8cf29e6baf6");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.FISHERMAN, Type.SNOW), "61d644761f706d31c99a593c8d5f7cbbd4372d73fbee8464f482fa6c139d97d4");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.FISHERMAN, Type.SWAMP), "d223664886d96643d3afdc4a2ac72ebc2bcd229e517519e15c3e7c1570ad745e");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.FISHERMAN, Type.TAIGA), "ac7f44b511f7063187d0fe12c74a0cb8c93f34d0f587338b2a9c22f3fa2f212");

        // Fletcher
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.FLETCHER, Type.DESERT), "fd4d13baf65ee197dfc6ec28657d25d89f472662204e13cbf761f9412c891335");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.FLETCHER, Type.JUNGLE), "99e7c054a8ab2d7f6447de438d337323632e9c3fcdb30d39ac336b523f84e3e6");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.FLETCHER, Type.PLAINS), "17532e90c573a394c7802aa4158305802b59e67f2a2b7e3fd0363aa6ea42b841");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.FLETCHER, Type.SAVANNA), "fac222efc6ab4bd1c70351de007e37bf3d028c1d93f08efeaea8a07ecb62867b");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.FLETCHER, Type.SNOW), "1e126158220e946517b2c20e13805928df06e19c1101bdea2bc5a4fc95f9c011");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.FLETCHER, Type.SWAMP), "12ed931ee6b77539d2fd7d271617db6b19365cb0e3c52eac005272301d07ac74");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.FLETCHER, Type.TAIGA), "99c45ed238e0b0c664dde09de64ba9e6038dd231dd58466fbdcdcc911e0ffbd2");

        // Leatherworker
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.LEATHERWORKER, Type.DESERT), "ee61ecbb86487ed16fedb275db92c9c5043a830f52d973ba44b28a7742006b43");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.LEATHERWORKER, Type.JUNGLE), "22a354db4b0a732dd847dfe4ad0a0729abbc7b8c500b9e6381950ce856f1de39");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.LEATHERWORKER, Type.PLAINS), "f76cf8b7378e889395d538e6354a17a3de6b294bb6bf8db9c701951c68d3c0e6");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.LEATHERWORKER, Type.SAVANNA), "f45c99c80d0345c4be3fc3a2f0d05a3e23a5c4bf7e991568eee64a6806f048c0");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.LEATHERWORKER, Type.SNOW), "b0e4aa6f5455e321059e202abc9d9e23675663070e92a079e8cb544f7be4c755");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.LEATHERWORKER, Type.SWAMP), "5007c4a3e3b8d31b94a95173bc2aea4b718c150f0166f0c964ca9e04be664a22");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.LEATHERWORKER, Type.TAIGA), "70c6c3a913af9293d6372b0bd4c2de2cc6d3b6e473e3fa0fff034741a612829d");

        // Librarian
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.LIBRARIAN, Type.DESERT), "ebff5901b97efef922555325e910a6d35cc46967ff8a7c2a0e5753af23ddcff2");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.LIBRARIAN, Type.JUNGLE), "7e5995106d080f10b2052de08e355f34a2213904d9d32f6dc2d1b27bec753b74");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.LIBRARIAN, Type.PLAINS), "e66a53fc707ce1ff88a576ef40200ce8d49fae4acad1e3b3789c7d1cc1cc541a");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.LIBRARIAN, Type.SAVANNA), "71f714133ce78d1181c4d5d3e53711ece10c4c9a28201188ee1a6f35cc0fa3ca");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.LIBRARIAN, Type.SNOW), "1806f9767f087e3e4c09ad012bfd063d013ba4c3169fbb0efd7538e28d7d83d");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.LIBRARIAN, Type.SWAMP), "e7473596a1cb40cf1b3ec5f46f2f9d590d5e78d0507680a7b9bc4304587da0c9");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.LIBRARIAN, Type.TAIGA), "74f04eb20cd0b82aaef2520aed9867c3cca247a4cd975a12ea50df03a7176241");

        // Mason
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.MASON, Type.DESERT), "4c4d7ea038187770cc2e4817c9209e19b74f5d288ed633281ecccaf5c8ebc767");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.MASON, Type.JUNGLE), "2fb93930f9d10a05adb84a6816bee930d52944a5e1a67f90f65c7fec4ec5a68a");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.MASON, Type.PLAINS), "2c02c3ffd5705ab488b305d57ff0168e26de70fd3f739e839661ab947dff37b1");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.MASON, Type.SAVANNA), "d00364c98af059ae6d581fca6038bee14b869998fb3aa382b3c4775d54e8481f");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.MASON, Type.SNOW), "f6a5a4b492cf3861d3044a911e1364dadf7a2be41fb2f9a5c619de5cc9a5af00");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.MASON, Type.SWAMP), "4b17427d4e9d89fa1e2cb297cd146ed2fdb49721a0eabf048e7e7d24c73fcda5");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.MASON, Type.TAIGA), "621ec612f8f78984a08f8290bd3f1c1892b4f7827b524dbab7eaacc9dd9e22b2");

        // Nitwit, None
        for (Profession profession : new Profession[] { Profession.NITWIT, Profession.NONE }) {
            addHead(map, new VillagerIdentifier(EntityType.VILLAGER, profession, Type.DESERT), "787cb532f85b33b3b141020aa051c35dc8e9cc0ae13ea258f1dfe5e0445f3bcc");
            addHead(map, new VillagerIdentifier(EntityType.VILLAGER, profession, Type.JUNGLE), "44b062a9f8399dccb6251a74e618647342a3c0240ca56f34614d52f60a3fecec");
            addHead(map, new VillagerIdentifier(EntityType.VILLAGER, profession, Type.PLAINS), "d14bff1a38c9154e5ec84ce5cf00c58768e068eb42b2d89a6bbd29787590106b");
            addHead(map, new VillagerIdentifier(EntityType.VILLAGER, profession, Type.SAVANNA), "522568354f535b094035cee868a4f7985788bd5755b80c0dc8dfc443969faea7");
            addHead(map, new VillagerIdentifier(EntityType.VILLAGER, profession, Type.SNOW), "20c641e3d3764ed1c1f1907c4334e2b1303e2152b13d1eb0c605763f97fb258a");
            addHead(map, new VillagerIdentifier(EntityType.VILLAGER, profession, Type.SWAMP), "51df1fd0f9937c631c6ec26e4b4ec61dd6ba1dfb2ba078f46379d993ee88d735");
            addHead(map, new VillagerIdentifier(EntityType.VILLAGER, profession, Type.TAIGA), "37d2147ac47a1c9588557f92f83109262a93ecf32170aa8b62056e1629f790a2");
        }

        // Shepherd
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.SHEPHERD, Type.DESERT), "ce36c366aeb30385cff151a8cf90bac5a8979a55bc2a808875e233d0f81b24a9");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.SHEPHERD, Type.JUNGLE), "e38bc381f74ce58e86d9d417d5125fb72e758c58d93f34cafc86d1fc51447d53");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.SHEPHERD, Type.PLAINS), "19e04a752596f939f581930414561b175454d45a0506501e7d2488295a5d5de");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.SHEPHERD, Type.SAVANNA), "431c0f4603b51eb6d8892f8ec0b520979041d671b8d378c8b26d097b8f7e1327");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.SHEPHERD, Type.SNOW), "d1644a552dd06f797413c002d41da52904a7bcfd744c5d2c1fe348d9f66cbbeb");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.SHEPHERD, Type.SWAMP), "69cf18b0447ff1dfcfba9e4c4ac7f6e26a986352dd1878c6a1e00d0f5dd6211d");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.SHEPHERD, Type.TAIGA), "45771b738349d1de0e01e894ae401686f598ca19a2088b095149fde9b76a4377");

        // Toolsmith
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.TOOLSMITH, Type.DESERT), "29d904fdae68fb120e9ae0f3537460f2a7c1de9159ab3f2b44c844048febabeb");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.TOOLSMITH, Type.JUNGLE), "16ec61097e11bfe6f10aaa12e5c0a54c829bdbd9d9d7a32fc627e6b5a931e77");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.TOOLSMITH, Type.PLAINS), "7dfa07fd1244eb8945f4ededd00426750b77ef5dfbaf03ed775633459ece415a");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.TOOLSMITH, Type.SAVANNA), "7d586f55be429db689c070c47aa9b1284cd51da493768559d7132df8c8916aed");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.TOOLSMITH, Type.SNOW), "fe7db3a5cb5dd6811fa87e2d113aa6057c669078dd62ff28b377f168277d95ce");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.TOOLSMITH, Type.SWAMP), "ad074b26b09c67feefea4e0245f63306e45cb935e98dbfaa3020eb40c7069719");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.TOOLSMITH, Type.TAIGA), "1a851258491341c00149a9c92de1acde665b131c8a74c9ffe0cb1e3a5ad9749");

        // Weaponsmith
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.WEAPONSMITH, Type.DESERT), "ebba69f6ee3e128bc2feec78c247b2a2f00c3aea11d8906c728de92c60a542ed");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.WEAPONSMITH, Type.JUNGLE), "25fafa2be55bd15aea6e2925f5d24f8068e0f4a2616f3b92b380d94912f0ec5f");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.WEAPONSMITH, Type.PLAINS), "5e409b958bc4fe045e95d325e6e97a533137e33fec7042ac027b30bb693a9d42");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.WEAPONSMITH, Type.SAVANNA), "c1beaa099c823332e7780a32110f5b0bfc2546e53fde8e206817325894018f3");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.WEAPONSMITH, Type.SNOW), "2844e3ffcc17d4ab0d0eebb6bfdb9603e2f7a095d700028c9db275ae1a95e7f2");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.WEAPONSMITH, Type.SWAMP), "4625c64beded1875b8cd9fdf810f16430e74197371572024b7307f26637573f6");
        addHead(map, new VillagerIdentifier(EntityType.VILLAGER, Profession.WEAPONSMITH, Type.TAIGA), "8e02febb4c52db1fb9e1e5c852a4e72d8dfe6c4c055a4649abf3d357d233fc1b");
    }

    private static void addZombieVillagers(Map<EntityIdentifier, MobHeadData> map) {
        // Armorer
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.ARMORER, Type.DESERT), "625afec4f8ac72c349c5b5cb4cc66935333ce4456746814ab58f4b3c92c238b9");
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.ARMORER, Type.JUNGLE), "317ab1f4a31be52a6c995e240878f01824cc905439d6642565d6091c641af8ea");
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.ARMORER, Type.PLAINS), "317ab1f4a31be52a6c995e240878f01824cc905439d6642565d6091c641af8ea");
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.ARMORER, Type.SAVANNA), "9c245379093542564e5e13b068659381fad31960467425c982c2eae20263f60d");
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.ARMORER, Type.SNOW), "d7595d772d85ced188bb6b05277dd909891a15a4bb87ca7b76346837f395226d");
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.ARMORER, Type.SWAMP), "fac68897d7149ae67da8f4bad7ac63fc7cbdb94af52de0e4ad7b6db9f9cf349c");
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.ARMORER, Type.TAIGA), "70413a1422cc80d9dd5ebc89ef489256c7d288839aa37e3cc712eed9e979b9a1");

        // Butcher
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.BUTCHER, Type.DESERT), "aa2534224c93588ac40177486ad1bc86668bc8f0b6ca8d083e3abd2a51cd9aba");
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.BUTCHER, Type.JUNGLE), "7c8dcbdee983d254f9443670e2f7bf4679cd099232bfe6ef693c4e24ccb9b66d");
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.BUTCHER, Type.PLAINS), "7c8dcbdee983d254f9443670e2f7bf4679cd099232bfe6ef693c4e24ccb9b66d");
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.BUTCHER, Type.SAVANNA), "e809c16cfe6de25a083384dd66d7636b4a35dab4e54558a2acb19193a1b28737");
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.BUTCHER, Type.SNOW), "6a55f3d926573ad665ab845867106ffaaeef9421e68427ffb27177a07a771872");
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.BUTCHER, Type.SWAMP), "9f16584632160381b3c483e7174b2ee57c24276a187cfee0740a4a5c687ffad7");
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.BUTCHER, Type.TAIGA), "444bca68151d59f22147e2eeb12343fe992715bd049b27943120b142790a6b9");

        // Cartographer
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.CARTOGRAPHER, Type.DESERT), "2835374210c6c143d4db32f498187723889c23f59c7efdfe8fcd440c0bbfae39");
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.CARTOGRAPHER, Type.JUNGLE), "d8d00c15b8feba87b1404663bdb15ae75e40e49ed140635ac80f438b52c221ec");
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.CARTOGRAPHER, Type.PLAINS), "d8d00c15b8feba87b1404663bdb15ae75e40e49ed140635ac80f438b52c221ec");
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.CARTOGRAPHER, Type.SAVANNA), "14035aca26be97be8406406f1557fa990738770fe038194a4ab81ce0c8396c72");
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.CARTOGRAPHER, Type.SNOW), "eab2ffa67277b05d8a5693c4fa6caab27714d9a9e56e46e21621a578bc71bbfd");
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.CARTOGRAPHER, Type.SWAMP), "7f049dfa3fc4d2f72e9ea9b960f07d12369fe77a042ec70058f2aa6532135fa9");
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.CARTOGRAPHER, Type.TAIGA), "ff2d5f830b4815c90bf4edd7c0c09bd7c915facd0c28ad0fd439f9b024c90b27");

        // Cleric
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.CLERIC, Type.DESERT), "b89d85f45c87f10b3de421426e3a83718d7806cefb0204bea0c629f363150f3f");
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.CLERIC, Type.JUNGLE), GENERIC_ZOMBIE_CLERIC);
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.CLERIC, Type.PLAINS), GENERIC_ZOMBIE_CLERIC);
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.CLERIC, Type.SAVANNA), "80568c2762b2ae6585c23a26d85cfd308f1218355e1ecd3b11f59b4024fbb133");
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.CLERIC, Type.SNOW), "4e687acf909ef6d399532829b7d06a6b1606782fcc9857716e83247133052520");
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.CLERIC, Type.SWAMP), "1289af53f506cb52bc2050649e5a7adc1199fa4fc4f9fff1854da5eb60966b58");
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.CLERIC, Type.TAIGA), GENERIC_ZOMBIE_CLERIC);

        // Farmer, Fisherman, Fletcher
        for (Type type : Type.values()) {
            addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.FARMER, type), "bab731b3253f03ab73923c93f62d1b14490d412a864a68cd3c15750e044083d9");
            addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.FISHERMAN, type), "e6a809495549d3c5b70d06a28a5fa6bed765da7673c6d3603c6498b368bcfc36");
            addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.FLETCHER, type), "695722fc8f8723702c325caf4b6af651f9dcbc31086a7270a25a3d3c0e162c01");
        }

        // Leatherworker
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.LEATHERWORKER, Type.DESERT), GENERIC_DESERT_ZOMBIE);
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.LEATHERWORKER, Type.JUNGLE), GENERIC_ZOMBIE_VILLAGER);
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.LEATHERWORKER, Type.PLAINS), GENERIC_ZOMBIE_VILLAGER);
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.LEATHERWORKER, Type.SAVANNA), GENERIC_SAVANNA_ZOMBIE);
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.LEATHERWORKER, Type.SNOW), GENERIC_SNOW_ZOMBIE);
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.LEATHERWORKER, Type.SWAMP), GENERIC_SWAMP_ZOMBIE);
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.LEATHERWORKER, Type.TAIGA), GENERIC_TAIGA_ZOMBIE);

        // Librarian
        for (Type type : Type.values()) {
            addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.LIBRARIAN, type), "7e38e8af075701ad40de5b2ef18e1ed39678e8558444ee91d563d5e5aeb32fab");
        }

        // Mason, Nitwit, None
        for (Profession profession : new Profession[] { Profession.MASON, Profession.NITWIT, Profession.NONE }) {
            addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, profession, Type.DESERT), GENERIC_DESERT_ZOMBIE);
            addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, profession, Type.JUNGLE), GENERIC_ZOMBIE_VILLAGER);
            addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, profession, Type.PLAINS), GENERIC_ZOMBIE_VILLAGER);
            addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, profession, Type.SAVANNA), GENERIC_SAVANNA_ZOMBIE);
            addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, profession, Type.SNOW), GENERIC_SNOW_ZOMBIE);
            addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, profession, Type.SWAMP), GENERIC_SWAMP_ZOMBIE);
            addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, profession, Type.TAIGA), GENERIC_TAIGA_ZOMBIE);
        }

        // Shepherd
        for (Type type : Type.values()) {
            addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.SHEPHERD, type), "91cf0d9ad1034c34c04cb4d1d31303947e852c7573f3a4c47998030d656609bb");
        }

        // Toolsmith
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.MASON, Type.DESERT), GENERIC_DESERT_ZOMBIE);
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.MASON, Type.JUNGLE), GENERIC_ZOMBIE_VILLAGER);
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.MASON, Type.PLAINS), GENERIC_ZOMBIE_VILLAGER);
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.MASON, Type.SAVANNA), GENERIC_SAVANNA_ZOMBIE);
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.MASON, Type.SNOW), GENERIC_SNOW_ZOMBIE);
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.MASON, Type.SWAMP), GENERIC_SWAMP_ZOMBIE);
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.MASON, Type.TAIGA), GENERIC_TAIGA_ZOMBIE);

        // Weaponsmith
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.WEAPONSMITH, Type.DESERT), "649adf83a4f6e37fe3f61b46d73ebd8a342d1c75dd4835cbd299d2968d7023f4");
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.WEAPONSMITH, Type.JUNGLE), "f354a4172a9ba9c47fb853ab284fdc0a344326013e5d73c4bec7800d83f4e399");
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.WEAPONSMITH, Type.PLAINS), "f354a4172a9ba9c47fb853ab284fdc0a344326013e5d73c4bec7800d83f4e399");
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.WEAPONSMITH, Type.SAVANNA), "720429b3b9afa589086c1df68080dfcfe72824d65116d333856d19869b3f5f7a");
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.WEAPONSMITH, Type.SNOW), "fb68bb674a93c6b8d687c35200ada3afabaa940ddeede4a2d537e58dd741be1e");
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.WEAPONSMITH, Type.SWAMP), "62266ebadf7db4055becbcdc37541517996319c661aae981d30ac64b67877add");
        addHead(map, new VillagerIdentifier(EntityType.ZOMBIE_VILLAGER, Profession.WEAPONSMITH, Type.TAIGA), "ffe54f0fdcb17d8883de2b8ff79090dee3e9d8cef5633f5a941c6cdd24f1dfb0");
    }

    private MobHeads() {
    }
}
