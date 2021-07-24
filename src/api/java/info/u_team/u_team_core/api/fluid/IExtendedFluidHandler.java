/**
 * This class is from mekanism:
 * https://github.com/mekanism/Mekanism/blob/dc479ad1674fd2cbbc91eb701d9fea4410d06bbe/src/api/java/mekanism/api/fluid/IExtendedFluidHandler.java
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

import info.u_team.u_team_core.api.InteractionType;
import net.minecraftforge.fluids.FluidStack;

import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public interface IExtendedFluidHandler extends IFluidHandlerModifiable {
	
	FluidStack insertFluid(int tank, FluidStack stack, InteractionType action);
	
	FluidStack extractFluid(int tank, int amount, InteractionType action);
	
	default FluidStack insertFluid(FluidStack stack, InteractionType action) {
		return ExtendedFluidHandlerUtils.insert(stack, action, this::getTanks, this::getFluidInTank, this::insertFluid);
	}
	
	default FluidStack extractFluid(int amount, InteractionType action) {
		return ExtendedFluidHandlerUtils.extract(amount, action, this::getTanks, this::getFluidInTank, this::extractFluid);
	}
	
	default FluidStack extractFluid(FluidStack stack, InteractionType action) {
		return ExtendedFluidHandlerUtils.extract(stack, action, this::getTanks, this::getFluidInTank, this::extractFluid);
	}
	
	@Override
	@Deprecated
	default int fill(FluidStack stack, FluidAction action) {
		return stack.getAmount() - insertFluid(stack, InteractionType.fromFluidAction(action)).getAmount();
	}
	
	@Override
	@Deprecated
	default FluidStack drain(FluidStack stack, FluidAction action) {
		return extractFluid(stack, InteractionType.fromFluidAction(action));
	}
	
	@Override
	@Deprecated
	default FluidStack drain(int amount, FluidAction action) {
		return extractFluid(amount, InteractionType.fromFluidAction(action));
	}
}