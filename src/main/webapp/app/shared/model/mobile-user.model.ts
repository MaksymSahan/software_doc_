import { IPayment } from 'app/shared/model/payment.model';
import { ITariffPlan } from 'app/shared/model/tariff-plan.model';
import { IAdmin } from 'app/shared/model/admin.model';
import { IOrderTariffPlan } from 'app/shared/model/order-tariff-plan.model';

export interface IMobileUser {
  id?: number;
  name?: string;
  surname?: string;
  email?: string;
  phone?: string;
  address?: string;
  payments?: IPayment[];
  tariffs?: ITariffPlan[];
  supportedBies?: IAdmin[];
  tariffPlanOrders?: IOrderTariffPlan[];
}

export class MobileUser implements IMobileUser {
  constructor(
    public id?: number,
    public name?: string,
    public surname?: string,
    public email?: string,
    public phone?: string,
    public address?: string,
    public payments?: IPayment[],
    public tariffs?: ITariffPlan[],
    public supportedBies?: IAdmin[],
    public tariffPlanOrders?: IOrderTariffPlan[]
  ) {}
}
