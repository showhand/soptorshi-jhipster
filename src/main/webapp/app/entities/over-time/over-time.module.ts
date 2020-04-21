import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    OverTimeComponent,
    OverTimeDeleteDialogComponent,
    OverTimeDeletePopupComponent,
    OverTimeDetailComponent,
    overTimePopupRoute,
    overTimeRoute,
    OverTimeUpdateComponent
} from './';

const ENTITY_STATES = [...overTimeRoute, ...overTimePopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        OverTimeComponent,
        OverTimeDetailComponent,
        OverTimeUpdateComponent,
        OverTimeDeleteDialogComponent,
        OverTimeDeletePopupComponent
    ],
    entryComponents: [OverTimeComponent, OverTimeUpdateComponent, OverTimeDeleteDialogComponent, OverTimeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiOverTimeModule {}
