import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    HolidayDeleteDialogExtendedComponent,
    HolidayDeletePopupExtendedComponent,
    HolidayDetailExtendedComponent,
    HolidayExtendedComponent,
    holidayExtendedRoute,
    holidayPopupRouteExtended,
    HolidayUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...holidayExtendedRoute, ...holidayPopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        HolidayExtendedComponent,
        HolidayDetailExtendedComponent,
        HolidayUpdateExtendedComponent,
        HolidayDeleteDialogExtendedComponent,
        HolidayDeletePopupExtendedComponent
    ],
    entryComponents: [
        HolidayExtendedComponent,
        HolidayUpdateExtendedComponent,
        HolidayDeleteDialogExtendedComponent,
        HolidayDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiHolidayModuleExtended {}
