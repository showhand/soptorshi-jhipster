import { Office } from 'app/shared/model/office.model';
import { Designation } from 'app/shared/model/designation.model';

export interface IAllowanceManagement {
    officeId?: number;
    designationId?: number;
}

export class AllowanceManagement implements IAllowanceManagement {
    constructor(public officeId?: number, public designationId?: number) {}
}
