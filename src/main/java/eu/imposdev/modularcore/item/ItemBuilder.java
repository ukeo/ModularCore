package eu.imposdev.modularcore.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ItemBuilder {

    // [Variables]
    private ItemStack itemStack;
    private ItemMeta itemMeta;
    private SkullMeta skullMeta;

    /**
     *
     * ItemBuilder constructor to create a ItemStack
     *
     * @param material the Material you want to use
     */
    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = this.itemStack.getItemMeta();
    }
    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = this.itemStack.getItemMeta();
    }

    /**
     *
     * Set the material for your itemStack
     *
     * @param material the material you want to use
     * @return constructor
     */
    public ItemBuilder setMaterial(Material material) {
        itemStack.setType(material);
        return this;
    }

    /**
     *
     * Set the subId for your ItemStack
     *
     * @param subid the subId as byte
     * @return constructor
     */

    @SuppressWarnings("deprecation")
    public ItemBuilder setSubid(byte subid) {
        itemStack.getData().setData(subid);
        return this;
    }

    /**
     *
     * Set the amount for your ItemStack
     *
     * @param amount the amount
     * @return constructor
     */

    public ItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    /**
     *
     * Set the name for your Material
     *
     * @param name the name
     * @return constructor
     */

    public ItemBuilder setName(String name) {
        this.itemMeta.setDisplayName(name);
        return this;
    }

    /**
     *
     * Set the durability for your ItemStack
     *
     * @param durability the durability
     * @return constructor
     */

    public ItemBuilder setDurability(short durability) {
        this.itemStack.setDurability(durability);
        return this;
    }

    /**
     *
     * Set the enchantments for your ItemStack
     *
     * @param enchantments Map with Enchantments and the level
     * @return constructor
     */
    public ItemBuilder setEnchantments(Map<Enchantment, Integer> enchantments) {
        enchantments.forEach((enchantment, level) ->  this.itemMeta.addEnchant(enchantment, level, true));
        return this;
    }

    /**
     *
     * Add a enchantment to your ItemStack
     *
     * @param enchantment the enchantment
     * @param level the level
     * @return constructor
     */

    public ItemBuilder addEnchantment(Enchantment enchantment, Integer level) {
        this.itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    /**
     *
     * Clear all enchantments for your ItemStack
     *
     * @return constructor
     */

    public ItemBuilder clearEnchantments() {
        this.itemStack.getEnchantments().keySet().forEach(enchantment -> this.itemStack.removeEnchantment(enchantment));
        return this;
    }

    /**
     *
     * Remove a single enchtmant from your ItemStack
     *
     * @param enchantment the enchantment
     * @return constructor
     */

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        this.itemStack.removeEnchantment(enchantment);
        return this;
    }

    /**
     *
     * Set the Lore for your ItemStack
     *
     * @param lore List<String> as lore
     * @return constructor
     */
    public ItemBuilder setLore(List<String> lore) {
        itemMeta.setLore(lore);
        return this;
    }

    /**
     *
     * Add a lore to an existing one
     *
     * @param lore the lore
     * @return constructor
     */

    public ItemBuilder addLore(String lore) {
        List<String> loreList = itemMeta.getLore();
        loreList.add(lore);
        itemMeta.setLore(loreList);
        return this;
    }

    /**
     *
     * Clear the lore for your ItemStack
     *
     * @return constructor
     */

    public ItemBuilder clearLore() {
        itemMeta.setLore(new ArrayList<>());
        return this;
    }

    /**
     *
     * Remove specific line form your lore
     *
     * @param lore the lore
     * @return constructor
     */

    public ItemBuilder removeLore(String lore) {
        itemMeta.getLore().remove(lore);
        return this;
    }

    /**
     *
     * Set the skull owner for the ItemStack
     *
     * @param owner the name of the owner
     * @return constructor
     */
    public ItemBuilder setSkullOwner(String owner) {
        itemStack.setItemMeta(itemMeta);
        skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwner(owner);
        return this;
    }

    /**
     *
     * Build the constructor and get a ItemStack
     *
     * @return ItemStack
     */
    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     *
     * Build the skull item
     *
     * @return ItemStack
     */

    @SuppressWarnings("deprecation")
    public ItemStack buildSkull() {
        itemStack.setItemMeta(skullMeta);
        itemStack.getData().setData((byte) 3);
        return itemStack;
    }

}
