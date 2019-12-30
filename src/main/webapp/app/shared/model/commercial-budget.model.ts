import { Moment } from 'moment';

export const enum CommercialOrderCategory {
    LOCAL = 'LOCAL',
    FOREIGN = 'FOREIGN'
}

export const enum CommercialCustomerCategory {
    ZONE = 'ZONE',
    FOREIGN = 'FOREIGN'
}

export const enum CommercialBudgetStatus {
    WAITING_FOR_APPROVAL = 'WAITING_FOR_APPROVAL',
    APPROVED = 'APPROVED',
    REJECTED = 'REJECTED'
}

export interface ICommercialBudget {
    id?: number;
    budgetNo?: string;
    type?: CommercialOrderCategory;
    customer?: CommercialCustomerCategory;
    budgetDate?: Moment;
    totalQuantity?: number;
    totalOfferedPrice?: number;
    totalBuyingPrice?: number;
    profitAmount?: number;
    profitPercentage?: number;
    budgetStatus?: CommercialBudgetStatus;
    proformaNo?: string;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
}

export class CommercialBudget implements ICommercialBudget {
    constructor(
        public id?: number,
        public budgetNo?: string,
        public type?: CommercialOrderCategory,
        public customer?: CommercialCustomerCategory,
        public budgetDate?: Moment,
        public totalQuantity?: number,
        public totalOfferedPrice?: number,
        public totalBuyingPrice?: number,
        public profitAmount?: number,
        public profitPercentage?: number,
        public budgetStatus?: CommercialBudgetStatus,
        public proformaNo?: string,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment
    ) {}
}
