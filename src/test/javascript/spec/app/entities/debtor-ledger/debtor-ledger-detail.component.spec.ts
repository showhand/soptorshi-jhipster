/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { DebtorLedgerDetailComponent } from 'app/entities/debtor-ledger/debtor-ledger-detail.component';
import { DebtorLedger } from 'app/shared/model/debtor-ledger.model';

describe('Component Tests', () => {
    describe('DebtorLedger Management Detail Component', () => {
        let comp: DebtorLedgerDetailComponent;
        let fixture: ComponentFixture<DebtorLedgerDetailComponent>;
        const route = ({ data: of({ debtorLedger: new DebtorLedger(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [DebtorLedgerDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DebtorLedgerDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DebtorLedgerDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.debtorLedger).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
