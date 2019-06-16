import { Designation } from 'app/shared/model/designation.model';
import { Office } from 'app/shared/model/office.model';
import { MonthType } from 'app/shared/model/monthly-salary.model';

export interface IPayrollManagement {
    officeId?: number;
    designationId?: number;
    monthType?: MonthType;
}

export class PayrollManagement implements IPayrollManagement {
    constructor(public officeId?: number, public designationId?: number, public monthType?: MonthType) {}
}
