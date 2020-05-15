import { IMobileUser } from 'app/shared/model/mobile-user.model';

export interface IPayment {
  id?: number;
  amount?: number;
  transactionProvider?: string;
  byWho?: IMobileUser;
}

export class Payment implements IPayment {
  constructor(public id?: number, public amount?: number, public transactionProvider?: string, public byWho?: IMobileUser) {}
}
