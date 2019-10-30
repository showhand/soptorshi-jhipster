import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    HolidayTypeDeleteDialogExtendedComponent,
    HolidayTypeDeletePopupComponentExtended,
    HolidayTypeDetailExtendedComponent,
    HolidayTypeExtendedComponent,
    holidayTypeExtendedRoute,
    holidayTypePopupRouteExtended,
    HolidayTypeUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...holidayTypeExtendedRoute, ...holidayTypePopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        HolidayTypeExtendedComponent,
        HolidayTypeDetailExtendedComponent,
        HolidayTypeUpdateExtendedComponent,
        HolidayTypeDeleteDialogExtendedComponent,
        HolidayTypeDeletePopupComponentExtended
    ],
    entryComponents: [
        HolidayTypeExtendedComponent,
        HolidayTypeUpdateExtendedComponent,
        HolidayTypeDeleteDialogExtendedComponent,
        HolidayTypeDeletePopupComponentExtended
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiHolidayTypeModuleExtended {}
