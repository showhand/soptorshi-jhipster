import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgJhipsterModule } from 'ng-jhipster';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { CookieModule } from 'ngx-cookie';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { MatButtonModule, MatTableModule } from '@angular/material';
import { AutoCompleteModule } from 'primeng/primeng';
import { Ng2SmartTableModule } from 'ng2-smart-table';

@NgModule({
    imports: [NgbModule.forRoot(), InfiniteScrollModule, CookieModule.forRoot(), FontAwesomeModule, MatButtonModule, AutoCompleteModule],
    exports: [
        FormsModule,
        CommonModule,
        NgbModule,
        NgJhipsterModule,
        InfiniteScrollModule,
        FontAwesomeModule,
        MatButtonModule,
        AutoCompleteModule,
        MatTableModule,
        Ng2SmartTableModule
    ]
})
export class SoptorshiSharedLibsModule {
    static forRoot() {
        return {
            ngModule: SoptorshiSharedLibsModule
        };
    }
}
