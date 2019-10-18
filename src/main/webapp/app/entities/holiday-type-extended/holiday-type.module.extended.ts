import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    HolidayTypeComponentExtended,
    HolidayTypeDetailComponentExtended,
    HolidayTypeUpdateComponentExtended,
    HolidayTypeDeletePopupComponentExtended,
    HolidayTypeDeleteDialogComponentExtended,
    holidayTypeRouteExtended,
    holidayTypePopupRouteExtended
} from './';

const ENTITY_STATES = [...holidayTypeRouteExtended, ...holidayTypePopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        HolidayTypeComponentExtended,
        HolidayTypeDetailComponentExtended,
        HolidayTypeUpdateComponentExtended,
        HolidayTypeDeleteDialogComponentExtended,
        HolidayTypeDeletePopupComponentExtended
    ],
    entryComponents: [
        HolidayTypeComponentExtended,
        HolidayTypeUpdateComponentExtended,
        HolidayTypeDeleteDialogComponentExtended,
        HolidayTypeDeletePopupComponentExtended
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiHolidayTypeModuleExtended {}
