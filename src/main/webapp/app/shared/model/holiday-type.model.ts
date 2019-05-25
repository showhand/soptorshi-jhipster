export const enum YesOrNo {
    YES = 'YES',
    NO = 'NO'
}

export interface IHolidayType {
    id?: number;
    name?: string;
    moonDependency?: YesOrNo;
}

export class HolidayType implements IHolidayType {
    constructor(public id?: number, public name?: string, public moonDependency?: YesOrNo) {}
}
