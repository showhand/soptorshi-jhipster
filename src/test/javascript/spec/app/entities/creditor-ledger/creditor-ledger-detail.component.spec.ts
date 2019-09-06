/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CreditorLedgerDetailComponent } from 'app/entities/creditor-ledger/creditor-ledger-detail.component';
import { CreditorLedger } from 'app/shared/model/creditor-ledger.model';

describe('Component Tests', () => {
    describe('CreditorLedger Management Detail Component', () => {
        let comp: CreditorLedgerDetailComponent;
        let fixture: ComponentFixture<CreditorLedgerDetailComponent>;
        const route = ({ data: of({ creditorLedger: new CreditorLedger(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CreditorLedgerDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CreditorLedgerDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CreditorLedgerDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.creditorLedger).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
