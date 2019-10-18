import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    HolidayComponentExtended,
    HolidayDetailComponentExtended,
    HolidayUpdateComponentExtended,
    HolidayDeletePopupComponentExtended,
    HolidayDeleteDialogComponentExtended,
    holidayRouteExtended,
    holidayPopupRouteExtended
} from './';

const ENTITY_STATES = [...holidayRouteExtended, ...holidayPopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        HolidayComponentExtended,
        HolidayDetailComponentExtended,
        HolidayUpdateComponentExtended,
        HolidayDeleteDialogComponentExtended,
        HolidayDeletePopupComponentExtended
    ],
    entryComponents: [
        HolidayComponentExtended,
        HolidayUpdateComponentExtended,
        HolidayDeleteDialogComponentExtended,
        HolidayDeletePopupComponentExtended
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiHolidayModuleExtended {}
