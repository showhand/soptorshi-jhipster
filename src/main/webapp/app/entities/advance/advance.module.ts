import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    AdvanceComponent,
    AdvanceDetailComponent,
    AdvanceUpdateComponent,
    AdvanceDeletePopupComponent,
    AdvanceDeleteDialogComponent,
    advanceRoute,
    advancePopupRoute
} from './';

const ENTITY_STATES = [...advanceRoute, ...advancePopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AdvanceComponent,
        AdvanceDetailComponent,
        AdvanceUpdateComponent,
        AdvanceDeleteDialogComponent,
        AdvanceDeletePopupComponent
    ],
    entryComponents: [AdvanceComponent, AdvanceUpdateComponent, AdvanceDeleteDialogComponent, AdvanceDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiAdvanceModule {}
