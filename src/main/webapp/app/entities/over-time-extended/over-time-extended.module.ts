import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    OverTimeDeleteDialogExtendedComponent,
    OverTimeDeletePopupExtendedComponent,
    OverTimeDetailExtendedComponent,
    OverTimeExtendedComponent,
    overTimeExtendedRoute,
    overTimePopupExtendedRoute,
    OverTimeUpdateExtendedComponent
} from './';
import { MyOverTimeComponent } from 'app/entities/over-time-extended/my-over-time.component';

const ENTITY_STATES = [...overTimeExtendedRoute, ...overTimePopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        OverTimeExtendedComponent,
        OverTimeDetailExtendedComponent,
        OverTimeUpdateExtendedComponent,
        OverTimeDeleteDialogExtendedComponent,
        OverTimeDeletePopupExtendedComponent,
        MyOverTimeComponent
    ],
    entryComponents: [
        OverTimeExtendedComponent,
        OverTimeUpdateExtendedComponent,
        OverTimeDeleteDialogExtendedComponent,
        OverTimeDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiOverTimeExtendedModule {}
