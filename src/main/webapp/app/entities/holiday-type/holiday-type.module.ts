import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    HolidayTypeComponent,
    HolidayTypeDetailComponent,
    HolidayTypeUpdateComponent,
    HolidayTypeDeletePopupComponent,
    HolidayTypeDeleteDialogComponent,
    holidayTypeRoute,
    holidayTypePopupRoute
} from './';

const ENTITY_STATES = [...holidayTypeRoute, ...holidayTypePopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        HolidayTypeComponent,
        HolidayTypeDetailComponent,
        HolidayTypeUpdateComponent,
        HolidayTypeDeleteDialogComponent,
        HolidayTypeDeletePopupComponent
    ],
    entryComponents: [HolidayTypeComponent, HolidayTypeUpdateComponent, HolidayTypeDeleteDialogComponent, HolidayTypeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiHolidayTypeModule {}
