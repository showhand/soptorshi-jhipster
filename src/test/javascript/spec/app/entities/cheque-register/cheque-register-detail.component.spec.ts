/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { ChequeRegisterDetailComponent } from 'app/entities/cheque-register/cheque-register-detail.component';
import { ChequeRegister } from 'app/shared/model/cheque-register.model';

describe('Component Tests', () => {
    describe('ChequeRegister Management Detail Component', () => {
        let comp: ChequeRegisterDetailComponent;
        let fixture: ComponentFixture<ChequeRegisterDetailComponent>;
        const route = ({ data: of({ chequeRegister: new ChequeRegister(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ChequeRegisterDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ChequeRegisterDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ChequeRegisterDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.chequeRegister).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
