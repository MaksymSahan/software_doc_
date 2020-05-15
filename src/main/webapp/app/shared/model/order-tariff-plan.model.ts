import { IMobileUser } from 'app/shared/model/mobile-user.model';

export interface IOrderTariffPlan {
  id?: number;
  name?: string;
  price?: number;
  whoOrdereds?: IMobileUser[];
}

export class OrderTariffPlan implements IOrderTariffPlan {
  constructor(public id?: number, public name?: string, public price?: number, public whoOrdereds?: IMobileUser[]) {}
}
