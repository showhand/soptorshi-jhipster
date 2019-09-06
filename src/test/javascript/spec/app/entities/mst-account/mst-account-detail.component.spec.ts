/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { MstAccountDetailComponent } from 'app/entities/mst-account/mst-account-detail.component';
import { MstAccount } from 'app/shared/model/mst-account.model';

describe('Component Tests', () => {
    describe('MstAccount Management Detail Component', () => {
        let comp: MstAccountDetailComponent;
        let fixture: ComponentFixture<MstAccountDetailComponent>;
        const route = ({ data: of({ mstAccount: new MstAccount(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [MstAccountDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MstAccountDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MstAccountDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.mstAccount).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
