package info.u_team.u_team_core.util.registry;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.api.registry.*;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.*;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * With some utility methods you can fetch static {@link IForgeRegistryEntry} fields from a class.
 * 
 * @author HyCraftHD
 *
 */
public class BaseRegistryUtil {
	
	/**
	 * Returns block items where the block implements {@link IUBlockRegistryType}. The registry name of the item block is
	 * set to the block's name.
	 * 
	 * @param blocks List with blocks. The registry names must be set
	 * @return List with block items
	 */
	public static List<BlockItem> getBlockItems(List<Block> blocks) {
		return blocks.parallelStream().filter(block -> block instanceof IUBlockRegistryType).map(block -> {
			final BlockItem blockItem = ((IUBlockRegistryType) block).getBlockItem();
			executeWithModContainer(block.getRegistryName().getNamespace(), () -> blockItem.setRegistryName(block.getRegistryName()));
			return blockItem;
		}).collect(Collectors.toList());
	}
	
	/**
	 * This method is caller sensitive! It use the current stack trace to get the caller class. If this is not what you want
	 * use {@link BaseRegistryUtil#getAllRegistryEntriesAndApplyNames(modid, classType, init)}. <br>
	 * 
	 * Returns all not excluded static fields of a class which extends the passed classType. Also search for
	 * {@link IUArrayRegistryType} fields with the given classType The classType must be a subclass of
	 * {@link IForgeRegistryEntry}. If the entry implements {@link IURegistryType} the
	 * {@link IForgeRegistryEntry#setRegistryName(ResourceLocation)} is executed to set the registry name.
	 * 
	 * With {@link Exclude} you can exclude fields from this list.
	 * 
	 * @see BaseRegistryUtil#getAllClassEntries(classType)
	 * 
	 * @param <T> Type of the {@link IForgeRegistryEntry} to search for
	 * @param modid The mod identifier to apply the names
	 * @param classType Class of the {@link IForgeRegistryEntry} to search for
	 * @return List of all found and matching entries with applied names if possible
	 */
	public static <T extends IForgeRegistryEntry<T>> List<T> getAllRegistryEntriesAndApplyNames(String modid, Class<T> classType) {
		return getAllRegistryEntriesAndApplyNames(modid, classType, getCallerClass());
	}
	
	/**
	 * Returns all not excluded static fields of a class which extends the passed classType. Also search for
	 * {@link IUArrayRegistryType} fields with the given classType The classType must be a subclass of
	 * {@link IForgeRegistryEntry}. If the entry implements {@link IURegistryType} the
	 * {@link IForgeRegistryEntry#setRegistryName(ResourceLocation)} is executed to set the registry name.
	 * 
	 * With {@link Exclude} you can exclude fields from this list.
	 * 
	 * @see BaseRegistryUtil#getAllClassEntries(classType, init)
	 * 
	 * @param <T> Type of the {@link IForgeRegistryEntry} to search for
	 * @param modid The mod identifier to apply the names
	 * @param classType Class of the {@link IForgeRegistryEntry} to search for
	 * @param init Class where to search for entries
	 * @return List of all found and matching entries with applied names if possible
	 */
	public static <T extends IForgeRegistryEntry<T>> List<T> getAllRegistryEntriesAndApplyNames(String modid, Class<T> classType, Class<?> init) {
		final List<T> list = getAllClassEntries(classType, init);
		list.parallelStream() //
				.filter(entry -> entry instanceof IURegistryType) //
				.forEach(entry -> executeWithModContainer(modid, () -> entry.setRegistryName(new ResourceLocation(modid, ((IURegistryType) entry).getEntryName()))));
		return list;
	}
	
	// Base methods which does not require IForgeRegistryEntry
	
	/**
	 * This method is caller sensitive! It use the current stack trace to get the caller class. If this is not what you want
	 * use {@link BaseRegistryUtil#getAllClassEntries(classType, init)}. <br>
	 * 
	 * Returns all not excluded static fields of a class which extends the passed classType. Also search for
	 * {@link IUArrayRegistryType} fields with the given classType.
	 * 
	 * With {@link Exclude} you can exclude fields from this list.
	 * 
	 * @see BaseRegistryUtil#getClassEntries(classType)
	 * @see BaseRegistryUtil#getClassEntriesFromArrayType(classType)
	 * 
	 * @param <T> Type of the entry to search for
	 * @param classType Class of the entry to search for
	 * @param init Class where to search for entries
	 * @return List of all found and matching entries
	 */
	public static <T> List<T> getAllClassEntries(Class<T> classType) {
		final Class<?> callerClass = getCallerClass();
		final List<T> list = getClassEntries(classType, callerClass);
		list.addAll(getClassEntriesFromArrayType(classType, callerClass));
		return list;
	}
	
	/**
	 * This method is caller sensitive! It use the current stack trace to get the caller class. If this is not what you want
	 * use {@link BaseRegistryUtil#getClassEntries(classType, init)}. <br>
	 * 
	 * Returns all not excluded static fields of a class which extends the passed classType.
	 * 
	 * With {@link Exclude} you can exclude fields from this list.
	 * 
	 * @param <T> Type of the entry to search for
	 * @param classType Class of the entry to search for
	 * @return List of all found and matching entries
	 */
	public static <T> List<T> getClassEntries(Class<T> classType) {
		return getClassEntries(classType, getCallerClass());
	}
	
	/**
	 * This method is caller sensitive! It use the current stack trace to get the caller class. If this is not what you want
	 * use {@link BaseRegistryUtil#getClassEntriesFromArrayType(classType, init)}. <br>
	 * 
	 * Returns all not excluded static fields of a class which extends the passed classType and are packed in and
	 * {@link IUArrayRegistryType}.
	 * 
	 * With {@link Exclude} you can exclude fields from this list.
	 * 
	 * @param <T> Type of the entry to search for
	 * @param classType Class of the entry to search for
	 * @return List of all found and matching entries
	 */
	public static <T> List<T> getClassEntriesFromArrayType(Class<T> classType) {
		return getClassEntriesFromArrayType(classType, getCallerClass());
	}
	
	/**
	 * Returns all not excluded static fields of a class which extends the passed classType. Also search for
	 * {@link IUArrayRegistryType} fields with the given classType.
	 * 
	 * With {@link Exclude} you can exclude fields from this list.
	 * 
	 * @see BaseRegistryUtil#getClassEntries(classType, init)
	 * @see BaseRegistryUtil#getClassEntriesFromArrayType(classType, init)
	 * 
	 * @param <T> Type of the entry to search for
	 * @param classType Class of the entry to search for
	 * @param init Class where to search for entries
	 * @return List of all found and matching entries
	 */
	public static <T> List<T> getAllClassEntries(Class<T> classType, Class<?> init) {
		final List<T> list = getClassEntries(classType, init);
		list.addAll(getClassEntriesFromArrayType(classType, init));
		return list;
	}
	
	/**
	 * Returns all not excluded static fields of a class which extends the passed classType.
	 * 
	 * With {@link Exclude} you can exclude fields from this list.
	 * 
	 * @param <T> Type of the entry to search for
	 * @param classType Class of the entry to search for
	 * @param init Class where to search for entries
	 * @return List of all found and matching entries
	 */
	public static <T> List<T> getClassEntries(Class<T> classType, Class<?> init) {
		return applyDefault(classType, Stream.of(init.getDeclaredFields())) //
				.collect(Collectors.toList());
	}
	
	/**
	 * Returns all not excluded static fields of a class which extends the passed classType and are packed in and
	 * {@link IUArrayRegistryType}.
	 * 
	 * With {@link Exclude} you can exclude fields from this list.
	 * 
	 * @param <T> Type of the entry to search for
	 * @param classType Class of the entry to search for
	 * @param init Class where to search for entries
	 * @return List of all found and matching entries
	 */
	public static <T> List<T> getClassEntriesFromArrayType(Class<T> classType, Class<?> init) {
		return applyDefault(IUArrayRegistryType.class, Stream.of(init.getDeclaredFields())) //
				.filter(arrayRegistryType -> arrayRegistryType.getArray().getClass().getComponentType().isAssignableFrom(classType)) // Exclude all array entries that are not a subclass
				.collect(() -> new ArrayList<>(), (list, arrayRegistryType) -> addGenericArrayElements(list, classType, arrayRegistryType.getArray()), (list, otherList) -> list.addAll(otherList));
	}
	
	// Internal methods
	
	/**
	 * Apply some default filter to this stream. Also map the field stream to the values of the fields.
	 * 
	 * @param <T> Type of the values
	 * @param classCast Cast values to this class
	 * @param stream Field stream
	 * @return Value stream
	 */
	private static <T> Stream<T> applyDefault(Class<T> classCast, Stream<Field> stream) {
		return stream.parallel() //
				.filter(field -> Modifier.isStatic(field.getModifiers())) // Exclude all non static fields
				.filter(field -> field.getDeclaredAnnotation(Exclude.class) == null) // Exclude all fields annotated with Exclude
				.filter(field -> classCast.isAssignableFrom(field.getType())) // Exclude all fields that are not a subclass
				.map(field -> getStaticField(classCast, field));
	}
	
	/**
	 * Returns the value of the field represented by the static field. Casts the value to the passed castClass argument.
	 * 
	 * @param <T> Type of the value
	 * @param classCast Cast entry of field to this class
	 * @param field Static field of a class
	 * @return Casted entry of the field
	 */
	private static <T> T getStaticField(Class<T> classCast, Field field) {
		try {
			return classCast.cast(field.get(null));
		} catch (Exception ex) {
			UCoreMain.logger.error("Failed fetching field entry {} for class {}.", field.getName(), field.getClass(), ex);
		}
		return null;
	}
	
	/**
	 * Adds the casted values of the generic (object) array to the list.
	 * 
	 * @param <T> Type of the values
	 * @param list List where to add the elements
	 * @param classCast Cast entries of array to this class
	 * @param array Generic (object) array of the type
	 */
	private static <T> void addGenericArrayElements(List<T> list, Class<T> classCast, Object[] array) {
		for (final Object entry : array) {
			list.add(classCast.cast(entry));
		}
	}
	
	/**
	 * Caller sensitive! This method gets the caller class if this is the first method call to this class. Can be only used
	 * in this class from other methods which are called directly from an other class.
	 * 
	 * @return Caller class
	 */
	private static Class<?> getCallerClass() {
		try {
			return Class.forName(Thread.currentThread().getStackTrace()[3].getClassName());
		} catch (ClassNotFoundException ex) {
			UCoreMain.logger.error("Failed the get caller class.", ex);
		}
		return null;
	}
	
	/**
	 * Sets the active mod to the modid if possible. Then calls the run method and resets the modid. This method must be is
	 * synchronized.
	 * 
	 * @param modid Mod that should be active
	 * @param runnable The code that should be executed with the mod container
	 */
	private static synchronized void executeWithModContainer(String modid, Runnable runnable) {
		final ModLoadingContext context = ModLoadingContext.get();
		final ModContainer activeContainer = context.getActiveContainer();
		final Object extention = context.extension();
		
		ModList.get().getModContainerById(modid).ifPresent(newContainer -> {
			context.setActiveContainer(newContainer, extention); // Set mod container to current modid
		});
		runnable.run();
		context.setActiveContainer(activeContainer, extention); // Reset mod container
	}
	
	/**
	 * Exclude an entry from fetching with {@link BaseRegistryUtil}.
	 * 
	 * @author HyCraftHD
	 */
	@Retention(value = RUNTIME)
	@Target(value = FIELD)
	public static @interface Exclude {
	}
	
}
