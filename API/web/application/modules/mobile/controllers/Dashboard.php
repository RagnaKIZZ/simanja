<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Dashboard extends MX_Controller {

	public function __construct() 
	{
        parent::__construct();
		
		//$this->auth->adminRestrict();
		//$this->load->library('datatables');
	}
	
	function index()
	{
		$id_user_mobile = $this->input->post('id_user_mobile');
		$token = $this->input->post('token');
		//$this->auth->adminRestrict();
		$data_pengaduan = $this->datamapper->query2("SELECT tb_pengaduan.* FROM tb_pengaduan WHERE tb_pengaduan.id_user = '$id_user_mobile' ");
		$c_dp = $this->datamapper->countQuery("SELECT tb_pengaduan.id_pengaduan FROM tb_pengaduan WHERE tb_pengaduan.id_user = '$id_user_mobile' ");
		$c_dp_proses = $this->datamapper->countQuery("SELECT tb_pengaduan.id_pengaduan FROM tb_pengaduan WHERE tb_pengaduan.id_user = '$id_user_mobile' AND status = '0' ");
		$c_dp_selesai = $this->datamapper->countQuery("SELECT tb_pengaduan.id_pengaduan FROM tb_pengaduan WHERE tb_pengaduan.id_user = '$id_user_mobile' AND status = '1' ");
		
		
		$output = array(
                'meta' => array(
                                    "code" => 200,
                                    "message" => "Berhasil"
                                ),
                'data' => array(
//								'data_pengaduan' => $data_pengaduan,
								'pengaduan_proses' => 'Jumlah '.$c_dp_proses,
								'pengaduan_selesai' =>'Jumlah '.$c_dp_selesai,
								'pengaduan_total' => 'Jumlah '.$c_dp
								)
            );
        print_r(json_encode($output));
	}


	
	function detailDashboard()
	{
		$id_user = $this->input->post('id_user');	
		$status = $this->input->post('status');	
//		$token_data = $thus->auth->tokenAuth($this->input->post('token'));
		if(empty($id_user) || empty($status))
		{
			$output = array(
					'meta' => array(
										"code" => 201,
										"message" => "Status dan ID User Tidak Boleh Kosong!"
									)
				);
			print_r(json_encode($output));
		} else {
				if($status === 'all'){
					$data_pengaduan = $this->datamapper->query2("SELECT
					id_pengaduan,
					judul,
					pesan,
					tgl_input,
					foto,
					alamat,
					lat,
					lng
					FROM
					simanja_db_2019.tb_pengaduan WHERE id_user = '$id_user'");
				}else if($status === 'finish'){
						$data_pengaduan = $this->datamapper->query2("SELECT
						id_pengaduan,
						judul,
						pesan,
						tgl_input,
						foto,
						alamat,
						lat,
						lng
						FROM
						simanja_db_2019.tb_pengaduan WHERE id_user = '$id_user' AND `status` = '1'");
				}else if($status === 'proses'){
						$data_pengaduan = $this->datamapper->query2("SELECT
						id_pengaduan,
						judul,
						pesan,
						tgl_input,
						foto,
						alamat,
						lat,
						lng
						FROM
						simanja_db_2019.tb_pengaduan WHERE id_user = '$id_user' AND `status` = '0'");
				}

				$output = array(
					'meta' => array(
										"code" => 200,
										"message" => "Berhasil"
									),
					'data' => $data_pengaduan,
								
				);
		print_r(json_encode($output));
			}
	
	}


	function detailitempengaduan()
	{
		$id_pengaduan = $this->input->post('id_pengaduan');	

//		$token_data = $thus->auth->tokenAuth($this->input->post('token'));
		if(empty($id_pengaduan))
		{
			$output = array(
					'meta' => array(
										"code" => 201,
										"message" => "Id Pengaduan tidak boleh kosong!",
									)
				);

		} else {
					$data = $this->datamapper->query2("SELECT
					id_pengaduan,
					judul,
					pesan,
					tgl_input,
					COALESCE(foto,'') as foto,
					COALESCE(alamat,'') as alamat,
					COALESCE(lat,'0') as lat,
					COALESCE(lng,'0') as lng
					FROM
					tb_pengaduan WHERE id_pengaduan = '$id_pengaduan'");

					$data_komen = $this->datamapper->query2("SELECT
					COALESCE(tb_pengaduan_reply.pesan,'') as komentar,
					COALESCE(tb_login.username,'') as username,
					COALESCE(tb_login.email,'') as email
					FROM
					tb_login
					RIGHT JOIN tb_pengaduan_reply
					ON tb_login.id = tb_pengaduan_reply.id_user
					WHERE id_pengaduan = '$id_pengaduan'");
			

				$output = array(
					'meta' => array(
										"code" => 200,
										"message" => "Berhasil"
									),
					'data' =>$data,
					'komentar' => $data_komen,
								
				);
			}
		print_r(json_encode($output));
			
		}
}