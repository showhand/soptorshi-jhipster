/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SalaryMessagesDetailComponent } from 'app/entities/salary-messages/salary-messages-detail.component';
import { SalaryMessages } from 'app/shared/model/salary-messages.model';

describe('Component Tests', () => {
    describe('SalaryMessages Management Detail Component', () => {
        let comp: SalaryMessagesDetailComponent;
        let fixture: ComponentFixture<SalaryMessagesDetailComponent>;
        const route = ({ data: of({ salaryMessages: new SalaryMessages(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SalaryMessagesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SalaryMessagesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SalaryMessagesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.salaryMessages).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
