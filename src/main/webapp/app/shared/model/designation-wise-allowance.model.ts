import { Moment } from 'moment';

export const enum AllowanceType {
    HOUSE_RENT = 'HOUSE_RENT',
    MEDICAL_ALLOWANCE = 'MEDICAL_ALLOWANCE',
    FESTIVAL_BONUS = 'FESTIVAL_BONUS',
    OVERTIME_ALLOWANCE = 'OVERTIME_ALLOWANCE',
    OTHER_ALLOWANCE = 'OTHER_ALLOWANCE'
}

export const enum AllowanceCategory {
    MONTHLY = 'MONTHLY',
    SPECIFIC = 'SPECIFIC'
}

export interface IDesignationWiseAllowance {
    id?: number;
    allowanceType?: AllowanceType;
    allowanceCategory?: AllowanceCategory;
    amount?: number;
    modifiedBy?: string;
    modifiedOn?: Moment;
    designationId?: number;
}

export class DesignationWiseAllowance implements IDesignationWiseAllowance {
    constructor(
        public id?: number,
        public allowanceType?: AllowanceType,
        public allowanceCategory?: AllowanceCategory,
        public amount?: number,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public designationId?: number
    ) {}
}
