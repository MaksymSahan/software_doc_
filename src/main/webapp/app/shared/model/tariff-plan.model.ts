import { IAdmin } from 'app/shared/model/admin.model';
import { IMobileUser } from 'app/shared/model/mobile-user.model';

export interface ITariffPlan {
  id?: number;
  name?: string;
  description?: string;
  internet?: string;
  calls?: string;
  sms?: string;
  price?: number;
  updatedBy?: IAdmin;
  users?: IMobileUser[];
}

export class TariffPlan implements ITariffPlan {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public internet?: string,
    public calls?: string,
    public sms?: string,
    public price?: number,
    public updatedBy?: IAdmin,
    public users?: IMobileUser[]
  ) {}
}
