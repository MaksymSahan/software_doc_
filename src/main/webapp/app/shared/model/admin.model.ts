import { ITariffPlan } from 'app/shared/model/tariff-plan.model';
import { IMobileUser } from 'app/shared/model/mobile-user.model';

export interface IAdmin {
  id?: number;
  name?: string;
  updatedTariffs?: ITariffPlan[];
  whoHelpeds?: IMobileUser[];
}

export class Admin implements IAdmin {
  constructor(public id?: number, public name?: string, public updatedTariffs?: ITariffPlan[], public whoHelpeds?: IMobileUser[]) {}
}
