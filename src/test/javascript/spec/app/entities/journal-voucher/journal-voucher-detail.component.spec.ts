/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { JournalVoucherDetailComponent } from 'app/entities/journal-voucher/journal-voucher-detail.component';
import { JournalVoucher } from 'app/shared/model/journal-voucher.model';

describe('Component Tests', () => {
    describe('JournalVoucher Management Detail Component', () => {
        let comp: JournalVoucherDetailComponent;
        let fixture: ComponentFixture<JournalVoucherDetailComponent>;
        const route = ({ data: of({ journalVoucher: new JournalVoucher(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [JournalVoucherDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(JournalVoucherDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(JournalVoucherDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.journalVoucher).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
