import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    StockInItemDeleteDialogExtendedComponent,
    StockInItemDeletePopupExtendedComponent,
    StockInItemDetailExtendedComponent,
    StockInItemExtendedComponent,
    stockInItemExtendedRoute,
    stockInItemPopupExtendedRoute,
    StockInItemUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...stockInItemExtendedRoute, ...stockInItemPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StockInItemExtendedComponent,
        StockInItemDetailExtendedComponent,
        StockInItemUpdateExtendedComponent,
        StockInItemDeleteDialogExtendedComponent,
        StockInItemDeletePopupExtendedComponent
    ],
    entryComponents: [
        StockInItemExtendedComponent,
        StockInItemUpdateExtendedComponent,
        StockInItemDeleteDialogExtendedComponent,
        StockInItemDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiStockInItemExtendedModule {}
