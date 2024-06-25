/**
 * This class is from mekanism:
 * https://github.com/mekanism/Mekanism/blob/dc479ad1674fd2cbbc91eb701d9fea4410d06bbe/src/api/java/mekanism/api/fluid/ExtendedFluidHandlerUtils.java
 * MIT License Copyright (c) 2017-2020 Aidan C. Brady Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions: The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package info.u_team.u_team_core.api.fluid;

import java.util.function.IntSupplier;

import info.u_team.u_team_core.api.InteractionType;
import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.neoforged.neoforge.fluids.FluidStack;

public class ExtendedFluidHandlerUtils {
	
	/**
	 * Util method for a generic insert implementation for various handlers. Mainly for internal use only
	 */
	public static FluidStack insert(FluidStack stack, InteractionType action, IntSupplier tankCount, Int2ObjectFunction<FluidStack> inTankGetter, InsertFluid insertFluid) {
		final int tanks = tankCount.getAsInt();
		if (tanks == 1) {
			return insertFluid.insert(0, stack, action);
		}
		final IntList matchingTanks = new IntArrayList();
		final IntList emptyTanks = new IntArrayList();
		for (int tank = 0; tank < tanks; tank++) {
			final FluidStack inTank = inTankGetter.get(tank);
			if (inTank.isEmpty()) {
				emptyTanks.add(tank);
			} else if (FluidStack.isSameFluidSameComponents(inTank, stack)) {
				matchingTanks.add(tank);
			}
		}
		FluidStack toInsert = stack;
		// Start by trying to insert into the tanks that have the same type
		for (final int tank : matchingTanks) {
			final FluidStack remainder = insertFluid.insert(tank, toInsert, action);
			if (remainder.isEmpty()) {
				// If we have no remaining fluid, return that we fit it all
				return FluidStack.EMPTY;
			}
			// Update what we have left to insert, to be the amount we were unable to insert
			toInsert = remainder;
		}
		for (final int tank : emptyTanks) {
			final FluidStack remainder = insertFluid.insert(tank, toInsert, action);
			if (remainder.isEmpty()) {
				// If we have no remaining fluid, return that we fit it all
				return FluidStack.EMPTY;
			}
			// Update what we have left to insert, to be the amount we were unable to insert
			toInsert = remainder;
		}
		return toInsert;
	}
	
	/**
	 * Util method for a generic extraction implementation for various handlers. Mainly for internal use only
	 */
	public static FluidStack extract(int amount, InteractionType action, IntSupplier tankCount, Int2ObjectFunction<FluidStack> inTankGetter, ExtractFluid extractFluid) {
		final int tanks = tankCount.getAsInt();
		if (tanks == 1) {
			return extractFluid.extract(0, amount, action);
		}
		FluidStack extracted = FluidStack.EMPTY;
		int toDrain = amount;
		for (int tank = 0; tank < tanks; tank++) {
			if (extracted.isEmpty() || FluidStack.isSameFluidSameComponents(extracted, inTankGetter.get(tank))) {
				// If there is fluid in the tank that matches the type we have started draining, or we haven't found a type yet
				final FluidStack drained = extractFluid.extract(tank, toDrain, action);
				if (!drained.isEmpty()) {
					// If we were able to drain something, set it as the type we have extracted/increase how much we have extracted
					if (extracted.isEmpty()) {
						extracted = drained;
					} else {
						extracted.grow(drained.getAmount());
					}
					toDrain -= drained.getAmount();
					if (toDrain == 0) {
						// If we are done draining break and return the amount extracted
						break;
					}
					// Otherwise keep looking and attempt to drain more from the handler, making sure that it is of
					// the same type as we have found
				}
			}
		}
		return extracted;
	}
	
	/**
	 * Util method for a generic extraction implementation for various handlers. Mainly for internal use only
	 */
	public static FluidStack extract(FluidStack stack, InteractionType action, IntSupplier tankCount, Int2ObjectFunction<FluidStack> inTankGetter, ExtractFluid extractFluid) {
		final int tanks = tankCount.getAsInt();
		if (tanks == 1) {
			final FluidStack inTank = inTankGetter.get(0);
			if (inTank.isEmpty() || !FluidStack.isSameFluidSameComponents(inTank, stack)) {
				return FluidStack.EMPTY;
			}
			return extractFluid.extract(0, stack.getAmount(), action);
		}
		FluidStack extracted = FluidStack.EMPTY;
		int toDrain = stack.getAmount();
		for (int tank = 0; tank < tanks; tank++) {
			if (FluidStack.isSameFluidSameComponents(stack, inTankGetter.get(tank))) {
				// If there is fluid in the tank that matches the type we are trying to drain, try to draining from it
				final FluidStack drained = extractFluid.extract(tank, toDrain, action);
				if (!drained.isEmpty()) {
					// If we were able to drain something, set it as the type we have extracted/increase how much we have extracted
					if (extracted.isEmpty()) {
						extracted = drained;
					} else {
						extracted.grow(drained.getAmount());
					}
					toDrain -= drained.getAmount();
					if (toDrain == 0) {
						// If we are done draining break and return the amount extracted
						break;
					}
					// Otherwise keep looking and attempt to drain more from the handler
				}
			}
		}
		return extracted;
	}
	
	@FunctionalInterface
	public interface InsertFluid {
		
		FluidStack insert(int tank, FluidStack stack, InteractionType action);
	}
	
	@FunctionalInterface
	public interface ExtractFluid {
		
		FluidStack extract(int tank, int amount, InteractionType action);
	}
}